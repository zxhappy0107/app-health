package cn.anshirui.store.appdevelop.note;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cn.hutool.json.JSONObject;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.model.SMSPayload;

@Category(SlowTests.class)
public class SMSClientUtils extends BaseTest {
	
	private static Logger LOG = LoggerFactory.getLogger(SMSClientUtils.class);
	private static SMSClient client = null;
	
	//发送验证码
	public static String SendSMSCode(String phone) {
		client = new SMSClient(MASTER_SECRET, APP_KEY);
		SMSPayload payload = SMSPayload.newBuilder()
				.setMobileNumber(phone)
				.setTempId(151336)
				.build();
		
		JsonObject json = new JsonObject();
		json.addProperty("mobile", phone);
		json.addProperty("temp_id", 151336);
		
		assertEquals(payload.toJSON(), json);
		String result = null;
		try {
			SendSMSResult res = client.sendSMSCode(payload);
			assertTrue(res.isResultOK());
			LOG.info(res.toString());
			JSONObject jsonObject = new JSONObject(res.toString());
			result = jsonObject.getStr("msg_id");
		} catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
        }
		return result;
	}
	
	//验证码校验
	public static boolean SendValidSMSCode(String msg_id,String code) {
		try {
			client = new SMSClient(MASTER_SECRET, APP_KEY);
			ValidSMSResult res = client.sendValidSMSCode(msg_id, code);
			assertEquals(true, res.getIsValid());
			LOG.info(res.toString());
			return true;
		} catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            return false;
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Message: " + e.getMessage());
            return false;
        }
	}


}
