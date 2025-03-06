package zhijianhu.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.green20220302.models.TextModerationPlusRequest;
import com.aliyun.green20220302.models.TextModerationPlusResponse;
import com.aliyun.green20220302.models.TextModerationPlusResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.green20220302.Client;

public class TextModerationPlusDemo {

    public static String DetectionText(String msg) {
        Config config = new Config();

        config.setAccessKeyId(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"));
        config.setAccessKeySecret(System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        //接入区域和地址请根据实际情况修改
        config.setRegionId("cn-shanghai");
        config.setEndpoint("green-cip.cn-shenzhen.aliyuncs.com");
        //读取时超时时间，单位毫秒（ms）。
        config.setReadTimeout(6000);
        //连接时超时时间，单位毫秒（ms）。
        config.setConnectTimeout(3000);
        //设置http代理。
        //config.setHttpProxy("http://xx.xx.xx.xx:xxxx");
        //设置https代理。
//        config.setHttpsProxy("https://xx.xx.xx.xx:xxxx");
        Client client = null;
        try {
            client = new Client(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JSONObject serviceParameters = new JSONObject();
        serviceParameters.put("content", msg);

        TextModerationPlusRequest textModerationPlusRequest = new TextModerationPlusRequest();
        // 检测类型
        textModerationPlusRequest.setService("llm_query_moderation");
        textModerationPlusRequest.setServiceParameters(serviceParameters.toJSONString());

        try {
            TextModerationPlusResponse response = client.textModerationPlus(textModerationPlusRequest);
            if (response.getStatusCode() == 200) {
                TextModerationPlusResponseBody result = response.getBody();
                Integer code = result.getCode();
                if (200 == code) {
                    TextModerationPlusResponseBody.TextModerationPlusResponseBodyData data = result.getData();
                    return data.getRiskLevel();//直接返回风险等级
                } else {
                    System.out.println("text moderation not success. code:" + code);
                }
            } else {
                System.out.println("response not success. status:" + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}