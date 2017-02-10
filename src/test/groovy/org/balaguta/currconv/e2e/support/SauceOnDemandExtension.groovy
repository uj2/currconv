package org.balaguta.currconv.e2e.support

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.SpecInfo

public class SauceOnDemandExtension extends AbstractAnnotationDrivenExtension<SauceOnDemand> {

    @Override
    public void visitSpecAnnotation(SauceOnDemand annotation, SpecInfo spec) {
        SauceOnDemandMethodInterceptor interceptor = new SauceOnDemandMethodInterceptor()
        spec.getBottomSpec().getAllFeatures().each { it.featureMethod.addInterceptor(interceptor) }
    }
}
