package org.balaguta.currconv.e2e.support

import com.saucelabs.common.SauceOnDemandAuthentication
import com.saucelabs.saucerest.SauceREST
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

class SauceOnDemandMethodInterceptor extends AbstractMethodInterceptor {

    private boolean verboseMode = true

    @Override
    void interceptFeatureMethod(IMethodInvocation invocation) throws Throwable {
        def success = true
        def driverField = invocation.spec.allFields.find { it.reflection.type.is(WebDriver) }
        def authenticationField = invocation.spec.allFields.find { it.reflection.type.is(SauceOnDemandAuthentication) }

        def updateJob = {
            def driver = (WebDriver) driverField.readValue(invocation.instance)
            def authentication = (SauceOnDemandAuthentication) authenticationField.readValue(invocation.instance)
            if (driver == null || !(driver instanceof RemoteWebDriver) || authentication == null) {
                return
            }

            def sessionId = driver.sessionId.toString()
            def name = "${invocation.feature.name} (${invocation.spec.name})".toString()
            SauceREST rest = new SauceREST(authentication.username, authentication.accessKey);
            rest.updateJobInfo(sessionId, [
                    'name': name,
                    'passed': success
            ])
        }

        try {
            invocation.proceed()
            updateJob()
        } catch (Throwable e) {
            success = false
            updateJob()
            throw e
        }
    }
}
