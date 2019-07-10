package metrics;

import com.gmail.kennethbgoodin.metrics.MetricKey;
import com.gmail.kennethbgoodin.metrics.MetricKeys;
import com.gmail.kennethbgoodin.metrics.Metrics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
public class MetricsTests {

    private final Metrics<HttpServletRequest, HttpServletResponse> metrics = Metrics.<HttpServletRequest, HttpServletResponse>builder()
            .idRequestsWith(req -> UUID.randomUUID().toString())
            .addHeaderToResponseWith(HttpServletResponse::addHeader)
            .withRepository(Metrics.inMemoryRepository())
            .build();

    private final MetricKey key = MetricKeys.RESPONSE_TIME_MS;

    @Before
    public void setup() {
        metrics.insertWithId(key, "a", 1);
        metrics.insertWithId(key, "b", 2);
        metrics.insertWithId(key, "c", 3);
    }

    @Test
    public void testMax() throws Exception {
        long max = metrics.max(key).map(Map.Entry::getValue).orElse(Long.MIN_VALUE);
        Assert.isTrue(max == 3, String.format("expected max of 3, got %s", max));
    }

    @Test
    public void testMin() throws Exception {
        long min = metrics.min(key).map(Map.Entry::getValue).orElse(Long.MAX_VALUE);
        Assert.isTrue(min == 1, String.format("expected min of 1, got %s", min));
    }

    @Test
    public void testAverage() throws Exception {
        double avg = metrics.average(key);
        Assert.isTrue(avg == 2.0D, String.format("expected avg of 2.0, got %f", avg));
    }

}
