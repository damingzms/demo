package net.zhping.web.bean;

/**
 * 
 * @author SAM
 *
 * @param <T>
 */
public class BaseResponse<T> {

    private String seqNo;
    private String code;
    private Object desc;
    private T value;

    public BaseResponse() {
    }

    public BaseResponse(String code, Object desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getDesc() {
		return desc;
	}

	public void setDesc(Object desc) {
		this.desc = desc;
	}

	public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
