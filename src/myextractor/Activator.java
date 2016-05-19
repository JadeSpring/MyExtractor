package myextractor;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import extract.ExecuteExtractor;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "MyExtractor"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
		
		/*long start = System.currentTimeMillis();
		
		//ExecuteExtractor.extracts("E:\\jEdits\\jedit-4-2-final");
		ExecuteExtractor.extracts("D:\\eclipse\\workspace\\MyExtractor");
		//ExecuteExtractor.extracts("D:\\eclipse\\workspace\\HelloWorld");
		//ExecuteExtractor.extracts("E:\\jEdits\\jedit-4-3");
		
		System.out.println("执行成功！");
		long end = System.currentTimeMillis();
		System.out.println("总用时：" + (end - start));*/
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
