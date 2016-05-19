package myextractor.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import dataUtil.DBManipulation;

import myextractor.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		/*store.setDefault(PreferenceConstants.P_BOOLEAN, true);
		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_STRING,
				"Default value");*/
		
		store.setDefault(PreferenceConstants.DATABASE, "MySQL");
		store.setDefault(PreferenceConstants.USER, "root");
		store.setDefault(PreferenceConstants.PASSWORD, "jade");
		
		store.addPropertyChangeListener(new IPropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				// TODO Auto-generated method stub
				if (event.getProperty().equals(PreferenceConstants.USER)) {
					String dbuser = event.getNewValue().toString().trim();
					DBManipulation.setUser(dbuser);
				}
				
				if (event.getProperty().equals(PreferenceConstants.PASSWORD)) {
					String dbpassword = event.getNewValue().toString().trim();
					DBManipulation.setPassword(dbpassword);
				}
				
				if (event.getProperty().equals(PreferenceConstants.DATABASE)) {
					String database = event.getNewValue().toString().trim();
					DBManipulation.setDatabase(database);
				}
			}
		});
	}

}
