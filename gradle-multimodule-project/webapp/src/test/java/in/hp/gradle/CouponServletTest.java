package in.hp.gradle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CouponServletTest {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        new CouponServlet().doGet(request, response);
        Assert.assertEquals(stringWriter.toString(), "SUPERSALE");
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("coupon")).thenReturn("SUPERSALE");
        when(request.getRequestDispatcher("response.jsp")).thenReturn(requestDispatcher);
        new CouponServlet().doPost(request, response);
        verify(request).setAttribute("discount", "Discount for coupon SUPERSALE is 50%");
        verify(requestDispatcher).forward(request, response);
    }
}