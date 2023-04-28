package com.example.genshinwiki.infrastructure.consts;

import cn.hutool.core.collection.ListUtil;

import java.util.List;

/**
 * @author zxd
 * @date 2022/6/4 16:36
 */
public class AppConst {
    public static final String APP_NAME = "时与风";

    public static class Dir {
        /**
         * 当前工作目录
         */
        public final static String USER_DIR = System.getProperty("user.dir");
        /**
         * 用户目录
         */
        public final static String USER_HOME = System.getProperty("user.home");
        /**
         * 图片资源文件夹
         */
        public static final String IMG_DIR = "/src/img";

        public static final String IMG_ROOT_PATH = "/img";

    }

    /**
     * 编码
     */
    public static class CharSet {
        public static final String GBK = "GBK";
        public static final String UYF8 = "UTF-8";
    }

    public static final List<String> IMG_LIST = ListUtil.toList(
            "mmexport1655088993555.jpg",
            "mmexport1655088991427.jpg",
            "mmexport1655088951748.jpg",
            "mmexport1655088966734.jpg",
            "mmexport1655088989534.jpg",
            "mmexport1655088970494.jpg",
            "mmexport1655088968667.jpg",
            "mmexport1655088961928.jpg",
            "mmexport1655088964300.jpg",
            "mmexport1655088995900.jpg",
            "mmexport1655088997885.jpg",
            "mmexport1655089001620.jpg",
            "mmexport1655089003698.jpg",
            "mmexport1655089006047.jpg",
            "mmexport1655089009294.jpg",
            "mmexport1655089013090.jpg",
            "mmexport1655089028733.jpg",
            "mmexport1655089030922.jpg",
            "mmexport1655089032880.jpg",
            "mmexport1655089035432.jpg",
            "mmexport1655089037325.jpg",
            "mmexport1655089039199.jpg",
            "mmexport1655089041143.jpg",
            "mmexport1655089042949.jpg",
            "mmexport1655089044848.jpg",
            "mmexport1655089094020.jpg",
            "mmexport1655089095934.jpg",
            "mmexport1655089109568.jpg",
            "mmexport1655089111395.jpg",
            "mmexport1655089113203.jpg",
            "mmexport1655089115537.jpg",
            "mmexport1655089119241.jpg",
            "mmexport1655089117396.jpg",
            "mmexport1655089121134.jpg",
            "mmexport1655089122934.jpg",
            "mmexport1655089124755.jpg",
            "mmexport1655089126573.jpg",
            "mmexport1655089128317.jpg",
            "mmexport1655089130079.jpg",
            "mmexport1655089131869.jpg",
            "mmexport1655089133636.jpg",
            "mmexport1655089135338.jpg",
            "mmexport1655089137417.jpg",
            "mmexport1655089143230.jpg");
}
