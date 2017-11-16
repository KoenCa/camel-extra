package org.apacheextras.camel.component.jcifs;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SmbThrowExceptionOnConnectionFailedTest extends BaseSmbTestSupport {
    private String getSmbUrl() {
        return "smb://" + getDomain() + ";" + "dummy" + "@localhost/" + getShare() + "/camel/" + getClass().getSimpleName() + "?password=" + "test"
                + "&fileExist=Override&throwExceptionOnConnectFailed=true&consumer.bridgeErrorHandler=true";
    }

    @Before
    public void setUpFileSystem() throws Exception {
        return;
    }

    @Test
    public void testBadLogin() {
        Exchange exchange = consumer.receive(getSmbUrl(), 5000);

        Exception exception = exchange.getException();
        Assert.assertNotNull(exception);
        Assert.assertEquals(GenericFileOperationFailedException.class, exception.getClass());
    }

    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(getSmbUrl()).to("mock:result");
            }
        };
    }
}
