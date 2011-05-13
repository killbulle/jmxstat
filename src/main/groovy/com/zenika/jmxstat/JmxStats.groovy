package com.zenika.jmxstat

import javax.management.remote.JMXConnectorFactory as JmxFactory
import javax.management.remote.JMXServiceURL as JmxUrl

import com.zenika.jmxstat.formatter.CsvLineFormatter
import com.zenika.jmxstat.formatter.LineFormatter
import com.zenika.jmxstat.formatter.NagiosFormatter
import com.zenika.jmxstat.formatter.OutPutFormatter
import javax.management.MBeanServerConnection
import javax.management.ObjectName
import com.zenika.jmxstat.monitoring.*

public class JmxStats {

	String servername = "127.0.0.1";
	int port = 9004;
	ServerInfo serverinfos;
	String filename;
	long t0;
	int nsamples;
	//temps echantillonages en ms
	int samplingtimes = 30000;
	int cut;
	int headerrepeat = 0;
	ObjectName query = new ObjectName('*:*')
	Map<ResultKey, Object> results = new LinkedHashMap<ResultKey, Object>();
	String[] allNames;
	Map<GroovyMBean, Monitoring> allmonitorings = new HashMap<GroovyMBean, Monitoring>();
	List<GroovyMBean> modulesListToMonitor = [];
	int formattercode;
	String login;
	String password;
	boolean verbose;



	private String getServerUrl(String servername, int port) {
		String url = "service:jmx:rmi:///jndi/rmi://${servername}:${port}/jmxrmi"
		return url
	}

	public JmxStats(String servername, int port, String login, String password, int nbsamples, int headerrepeat, int samplingtime, int formattercode, boolean verbose, int cut, String filename) {
		this.port = port;
		this.servername = servername;
		this.login = login;
		this.password = password;
		this.samplingtimes = samplingtime;
		this.nsamples = nbsamples;
		this.headerrepeat = headerrepeat;
		this.verbose = verbose
		this.cut = cut;
		this.filename = filename;
		this.formattercode = formattercode;

		MBeanServerConnection conn = createJmxConnection();
		serverinfos = new ServerInfo(servername, port, conn);
		allNames = conn.queryNames(query, null)
	}


	private MBeanServerConnection createJmxConnection() {
		MBeanServerConnection servercon = null;
		try {
			Hashtable attributes = new Hashtable()
			String[] buffer = [login, password]
			attributes.put("jmx.remote.credentials", (String[]) buffer)
			String serverUrl = getServerUrl(servername, port)
			if (verbose) {
				printf("Detailled connection\n")
				printf("%-20s %25s\n", "Connection URL", serverUrl)
				printf("%-20s %25s\n", "login", login)
				printf("%-20s %25s\n", "password", password)
			}
			servercon = JmxFactory.connect(new JmxUrl(serverUrl), attributes).MBeanServerConnection
		}
		catch (java.io.IOException e) {
			printf("%-20s %125s\n", "Connection Error	:", e.message);
			System.exit(0);
		}
		return servercon;
	}

	/**
	 * Register monitorings
	 * @param monitorings
	 */
	public void registerMonitorings(List<Monitoring> monitorings) {
		//on boucle sur les monitoring
		monitorings.each
		{ registerMonitoring(it); }
	}
	/**
	 * Register a monitoring
	 * @param monitoring
	 */
	public void registerMonitoring(Monitoring monitoring) {
		List modules = []
		modules = allNames.findAll { name ->
			monitoring.match(name)
		}.collect {
			new GroovyMBean(serverinfos.connection, it)
		}
		//we link monitoring and groovyMbeans here
		modules.eachWithIndex { gmbeans, id ->
			allmonitorings.put(gmbeans, monitoring)
		}
	}

	public Map collectlTimedDataInfos() {
		//clear previous result
		results.clear();
		//Add a time header per default
		TimeMetric timemetric = new TimeMetric();
		ResultKey time = new ResultKey("Time", 0, null, timemetric, serverinfos)
		results.put(time, System.currentTimeMillis());
		allmonitorings.eachWithIndex { mbeanmodule, monitoring, id ->
			monitoring.collectResults(results, mbeanmodule, id, serverinfos)
		}
		return results;
	}
	/**
	 * We loop and collect data
	 */
	public void loopCollectAndFormatData() {
		int sample = 1;
		Timer timer = new Timer();
		GroovyTimerTask task = new GroovyTimerTask();
		task.closure = {
			OutPutFormatter formatter = null;
			//TODO add enum?
			if (formattercode == 0) {
				formatter = new LineFormatter((cut == 0), headerrepeat, cut);
			}
			if (formattercode == 1) {
				formatter = new CsvLineFormatter(",");
			}
			if (formattercode == 2) {
				formatter = new NagiosFormatter();
			}
			if (formattercode == 3) {
				formatter = new BrutFormatter();
			}
			Map results = collectlTimedDataInfos();
			print formatter.format(results, sample);
			if (nsamples == sample) {
				System.exit(0);
			}
			sample++;
		}
		timer.schedule(task, 1000, samplingtimes)
	}

	/**
	 * Command line parsing logic
	 * @param args
	 * @return
	 */
	public static JmxStats parseCommandeLine(String[] args) {
		CliBuilder cli = new CliBuilder(usage: 'JmxStats.groovy ')
		// Create the list of options.
		cli.with {
			h longOpt: 'help', 'Show usage information'
			s longOpt: 'server', args: 1, argName: 'server', 'IP or hostname server', required: true
			p longOpt: 'port', args: 1, argName: 'port', 'jmx port', required: true
			i longOpt: 'interval', args: 1, argName: 'freq', 'frequency in ms to collect sample', required: true
			k longOpt: 'nb', args: 1, argName: 'nb', 'nb samples to collect', required: false
			u longOpt: 'user', args: 1, argName: 'user', 'jmx user'
			x longOpt: 'password', args: 1, argName: 'password', 'jmx password'
			r longOpt: 'repeat', args: 1, argName: 'repeat', 'repeatinterval'
			e longOpt: 'csv', "use csv format"
			b longOpt: 'brut', "use no format for stats metrics"
			m longOpt: 'centreon', "use centreon format"
			v longOpt: 'verbose', 'debug mode'
			f longOpt: 'file', args: 1, argName: 'file', 'fichier de config jmx ou repertoire', required: true
			c longOpt: 'cut', args: 1, argName: 'cut', 'column header name cut in  non csv mode', required: false
		}

		OptionAccessor options = cli.parse(args)

		// Show usage text when -h or --help option is used.
		if (!options) {
			System.exit(0);
		}
		if (options.h) {
			cli.usage()
		}
		//Défault value
		int repeat = 0;
		int cut = 0;//0 desactivé
		int nbsamples = Integer.MAX_VALUE;
		int interval = 30000;
		int port = 5000;
		String user = "";
		String password = "";
		int formattercode = 0;

		try {
			if (options.csv) {
				formattercode = 1
			}

			if (options.nb) {
				nbsamples = Integer.parseInt(options.nb);
			}

			if (options.repeat) {
				repeat = Integer.parseInt(options.repeat)
			}
			if (options.cut) {
				cut = Integer.parseInt(options.cut);
			}
			if (!options.user) {
				user = "";
			}
			else {
				user = options.user
			}
			if (!options.password) {
				password = "";
			}
			else {
				password = options.password
			}
			if (options.centreon) {
				formattercode = 2;
				nbsamples = 1;
			}
			if (options.brut) {
				formattercode = 3;
			}
			interval = Integer.parseInt(options.interval);
			port = Integer.parseInt(options.port)
		}

		catch (Exception e) {
			cli.usage();
			e.printStackTrace(System.out);
		}
		if (options.verbose) {
			println "Verbose mode parameters"
			String format = "%-20s :%25s\n"
			printf(format, "Server name", options.server);
			printf(format, "Server jmx port", options.port);
			printf(format, "Sampling freq", options.interval);
			printf(format, "Login jmx", options.user);
			printf(format, "Password jmx", options.password);
			printf(format, "Header repeat", options.repeat);
			printf(format, "Nb samples", options.nb);
			printf(format, "centreon", options.centreon);
			printf(format, "brut", options.brut);
			printf(format, "cut", options.cut);
			printf(format, "csv", options.csv);
			printf(format, "jmx file", options.file);
		}
		//TODO create a struc when options will be finalized ...
		JmxStats stats = new JmxStats(options.server, port, user, password, nbsamples, repeat, interval, formattercode, options.verbose, cut, options.file)
		return stats
	}



	public static void main(String[] args) {
		JmxStats stats = parseCommandeLine(args);
		// Load the class and create an instance
		stats.runStats(System.currentTimeMillis())
	}



	public void runStats(long t0) {
		this.t0 = t0;
		//Object Graph builder config
		List<Monitoring> monitorings = new ArrayList<Monitoring>();
		MonitoringGraphBuilder builder = new MonitoringGraphBuilder(classLoader: getClass().classLoader, monitorings: monitorings)
		builder.classNameResolver = "com.zenika.jmxstat.monitoring"
		builder.setChildPropertySetter(new MetricPropertySetter());

		String scripttext = buildMonitoringsScript(filename)

		//we register bindings
		Binding binding = new Binding(["monitorings": monitorings, "stats": this, "collector": builder]);
		Script dslScript = new GroovyShell(binding).parse(scripttext)
		dslScript.run();
		registerMonitorings(monitorings);
		//launch the data collection
		loopCollectAndFormatData();
	}
	/**
	 * Build the full monitoring sscipt
	 * @param filename
	 * @return
	 */
	private String buildMonitoringsScript(String filename) {
		File scripts = new File(filename);
		String scripttext = "";
		if (scripts.isDirectory()) {
			//we merge monitoring
			scripts.eachFileRecurse {file -> if(file.isFile() && file.name.endsWith(".groovy")){scripttext += file.text + "\n"}}
		}
		else {
			scripttext = scripts.text
		}
		if (verbose) {
			println "Using following monitoring";
			println scripttext;
		}
		return scripttext;
	}
}






















