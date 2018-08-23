package com.lhwbest.accessibilityservicedemo;

import com.sina.weibo.greenlight.base.BaseTestCase;
import com.sina.weibo.greenlight.context.CoreContext;

/**
 * Created by xiangbin1 on 2018/7/24.
 */
public class InitTestCase extends BaseTestCase {

    InitBusiness mInitBusiness;
    CoreContext coreContext;

    public InitTestCase() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        coreContext = new CoreContext(this);
        getActivity();
        mInitBusiness = new InitBusiness(coreContext);
    }

    /**
     * 用于点击初始化界面
     */
    public void testInit_001() {
        mInitBusiness.init_001();
    }

    @Override
    protected void tearDown() throws Exception {
        coreContext.finishOpenedActivities();
        super.tearDown();
    }
}
