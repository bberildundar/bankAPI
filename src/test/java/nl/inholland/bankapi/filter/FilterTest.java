package nl.inholland.bankapi.filter;

import jakarta.servlet.ServletException;
import nl.inholland.bankapi.configuration.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

class FilterTest {

    @Value("${application.max.content.size}")
    private int maxSize;

    @Test
    void testLargeRequestFilter() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(TestHelper.returnStringOfSize(maxSize).getBytes());
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        LargeRequestFilter filter = new LargeRequestFilter();

        Exception exception = Assertions.assertThrows(ServletException.class,
                () -> filter.doFilter(request, response, chain));
        Assertions.assertEquals("Request too large", exception.getMessage());
    }
}
