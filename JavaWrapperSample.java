import com.veracode.apiwrapper.AbstractAPIWrapper;
import com.veracode.apiwrapper.wrappers.AdminAPIWrapper;
import com.veracode.apiwrapper.wrappers.ArcherAPIWrapper;
import com.veracode.apiwrapper.wrappers.MitigationAPIWrapper;
import com.veracode.apiwrapper.wrappers.ResultsAPIWrapper;
import com.veracode.apiwrapper.wrappers.UploadAPIWrapper;
import com.veracode.apiwrapper.wrappers.VastAPIWrapper;

import com.veracode.apiwrapper.wrapper.cli.exceptions.ApiException;


public class JavaWrapperSample {
	public static void main (String[] args) {
		final boolean isApiCredential = false;
		final String username = "xxxxx";
	    	final String password = "xxxxx";
		final String appName = "xxxxx";

	    System.out.println("This is Java Wrapper API Sample code");

	    try {
			//call getAppList()
			String appListXml = getAppList(isApiCredential, username, password);
			System.out.println("\nThe following are getAppList() return XML");
			System.out.println("============================================");
			System.out.println(appListXml);

			// Get appId
			String appId = XmlUtil.parseAppId(appName, appListXml);
			if (StringUtil.isNullOrEmpty(appId)) {
			     throw new ApiException(String.format("Cannot find the ID for application %s", appName));
        	}

			System.out.println("\n***appId=" + appId + "***\n");

			// call getAppInfo()
			String appInfoXml = getAppInfo(appId, isApiCredential, username, password);
			String error = XmlUtil.getErrorString(appInfoXml);
			if (!StringUtil.isNullOrEmpty(error)) {
			    throw new ApiException(error);
        	}

        	System.out.println("\nThe following are getAppInfo() for appId = " + appId);
			System.out.println("============================================");
			System.out.println(appInfoXml);

			// call getBuildList()
			String buildListXml = getBuildList(appId, isApiCredential, username, password);
			error = XmlUtil.getErrorString(buildListXml);
			if (!StringUtil.isNullOrEmpty(error)) {
			    throw new ApiException(error);
        	}

        	System.out.println("\nThe following are getBuildList()");
			System.out.println("============================================");
			System.out.println(buildListXml);

			// call getBuildInfo()
			String buildInfoXml = getBuildInfo(appId, isApiCredential, username, password);
			error = XmlUtil.getErrorString(buildInfoXml);
			if (!StringUtil.isNullOrEmpty(error)) {
			    throw new ApiException(error);
        	}

        	System.out.println("\nThe following are getBuildInfo()");
			System.out.println("============================================");
			System.out.println(buildInfoXml);

			// Get buildId
			String buildId = XmlUtil.parseBuildId(buildInfoXml);
			if (StringUtil.isNullOrEmpty(buildId)) {
			     throw new ApiException(String.format("Cannot find the ID for build"));
        	}

			System.out.println("\n***buildId=" + buildId + "***\n");

			// call getDetailedReport()
			String detailReport = getDetailedReport(buildId, isApiCredential, username, password);

        	System.out.println("\nThe following are getDetailedReport()");
			System.out.println("============================================");
			System.out.println(detailReport);

		}
		catch (Exception e) {}

	}

	public static String getAppList(boolean isApiCredential, String username, String password) throws Exception{

		UploadAPIWrapper uploadApiWrapper = new UploadAPIWrapper();

		setupCredential(uploadApiWrapper, isApiCredential, username, password);
		String appListXml = uploadApiWrapper.getAppList();

		return appListXml;
	}

	/**
	 * Get the latest App info of an application
	 */
    public static final String getAppInfo(final String appId, boolean isApiCredential, final String username, final String password) throws Exception {
        if (StringUtil.isNullOrEmpty(appId)) {
            throw new IllegalArgumentException("Application ID is invalid.");
        }

        UploadAPIWrapper uploadApiWrapper = new UploadAPIWrapper();
        setupCredential(uploadApiWrapper, isApiCredential, username, password);

        String appInfoXml = uploadApiWrapper.getAppInfo(appId);
        String error = XmlUtil.getErrorString(appInfoXml);
        if (!StringUtil.isNullOrEmpty(error)) {
            throw new ApiException(error);
        }

        return appInfoXml;
    }

	/**
	 * Get the latest build list of an application
	 */
    public static final String getBuildList(final String appId, boolean isApiCredential, final String username, final String password) throws Exception {
        if (StringUtil.isNullOrEmpty(appId)) {
            throw new IllegalArgumentException("Application ID is invalid.");
        }

        UploadAPIWrapper uploadApiWrapper = new UploadAPIWrapper();
        setupCredential(uploadApiWrapper, isApiCredential, username, password);

        String buildListXml = uploadApiWrapper.getBuildList(appId);
        String error = XmlUtil.getErrorString(buildListXml);
        if (!StringUtil.isNullOrEmpty(error)) {
            throw new ApiException(error);
        }

        return buildListXml;
    }

	/**
	 * Get the latest build info of an application
	 */
    public static final String getBuildInfo(final String appId, boolean isApiCredential, final String username, final String password) throws Exception {
        if (StringUtil.isNullOrEmpty(appId)) {
            throw new IllegalArgumentException("Application ID is invalid.");
        }

        UploadAPIWrapper uploadApiWrapper = new UploadAPIWrapper();
        setupCredential(uploadApiWrapper, isApiCredential, username, password);

        String buildInfoXml = uploadApiWrapper.getBuildInfo(appId, null, null);
        String error = XmlUtil.getErrorString(buildInfoXml);
        if (!StringUtil.isNullOrEmpty(error)) {
            throw new ApiException(error);
        }

        return buildInfoXml;
    }


    /**
     * Get the detailed report of a given build (by ID)
     *
     * @param buildId - ID of a build
     * @param isApiCredential - A boolean value to indicate using API credential or not
     * @param userName - User name or the ID (based on the isApiCredential parameter)
     * @param password - Password or key (based on the isApiCredential parameter)
     * @return The detailed report in XML
     * @throws Exception when an error is encountered during the process
     */
    public static final String getDetailedReport(final String buildId, boolean isApiCredential, final String username, final String password) throws Exception {
        if (StringUtil.isNullOrEmpty(buildId)) {
            throw new IllegalArgumentException("Build ID is invalid.");
        }

        ResultsAPIWrapper resultsApiWrapper = new ResultsAPIWrapper();
        setupCredential(resultsApiWrapper, isApiCredential, username, password);

        return resultsApiWrapper.detailedReport(buildId);
    }

	public static final void setupCredential(AbstractAPIWrapper wrapper,
			boolean isApiCredential, String userName, String password) {

		if (null == wrapper) {
			return;
		}

		if (isApiCredential) {
			wrapper.setUpApiCredentials(userName, password);
		} else {
			wrapper.setUpCredentials(userName, password);
		}
	}


}
