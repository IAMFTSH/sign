package sign.entity.VO;

import lombok.Data;

/**
 * @author 邝明山
 * on 2021/3/5 10:21
 */
@Data
public class OpenId {
    private String openid;

    private String session_key;

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getSession_key() {
        return this.session_key;
    }

}
