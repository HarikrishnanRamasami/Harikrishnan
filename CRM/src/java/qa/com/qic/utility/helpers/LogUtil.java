package qa.com.qic.utility.helpers;

import org.apache.logging.log4j.LogManager;
/*import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.apache.logging.log4j.Logger;

/**
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * @author Karthick P <karthick.pandi@qicgroup.com.qa>
 */
public class LogUtil {

    public static Logger getLogger(Class clazz){
        //return LoggerFactory.getLogger(clazz);
        return LogManager.getLogger(clazz);
    }

}
