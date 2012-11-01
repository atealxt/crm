package org.claros.intouch.common.utility;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.exception.SystemException;

public class Initiator extends HttpServlet {
	private static final long serialVersionUID = -1124952420163920030L;
	private static Log log = LogFactory.getLog(Initiator.class);

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occure
	 */
	@Override
	public void init() throws ServletException {
		// delete all files in the tempDir
		File dir = new File(Constants.tmpDir);
		if (dir.exists()) {
			if (!dir.isDirectory()) {
				log.fatal("Temp dir is not a directory, please check the tmp-dir setting in the config.xml file");
				throw new SystemException();
			}
		} else {
			log.info("Create directory: " + Constants.tmpDir);
			if (!dir.mkdirs()) {
				log.fatal("Create directory fail!");
				throw new SystemException();
			}
		}
		String files[] = dir.list();
		for (int i = 0; i < files.length; i++) {
			File f = new File(Constants.tmpDir + "/" + files[i]);
			f.delete();
		}
	}

}
