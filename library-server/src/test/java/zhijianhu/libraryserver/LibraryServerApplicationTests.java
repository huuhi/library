package zhijianhu.libraryserver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class LibraryServerApplicationTests {

    @Test
    void contextLoads() {
        String jsonStr = "{\"code\":200,\"data\":{\"result\":[{\"description\":\"未检测出风险\",\"label\":\"nonLabel\"}],\"riskLevel\":\"none\"},\"msg\":\"OK\",\"requestId\":\"1BE84F43-9FEA-5F1F-B10F-5A20230B51AC\"}";

        // 解析 JSON
        JSONObject root = JSONObject.parseObject(jsonStr);
        JSONObject data = root.getJSONObject("data");
        JSONArray result = data.getJSONArray("result");

        // 获取第一个元素的 description
        if (!result.isEmpty()) {
            JSONObject firstItem = result.getJSONObject(0);
            String description = firstItem.getString("description");
            System.out.println("Description: " + description); // 输出：未检测出风险
        }
    }
}
