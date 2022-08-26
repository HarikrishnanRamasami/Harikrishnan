/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.vo;

import java.io.Serializable;
import java.util.List;

import qa.com.qic.common.vo.CrmDeal;

/**
 *
 * @author ravindar.singh
 */
public class KeyValue implements Serializable {

    private String key;
    private String value;
    private String info;
    private String info1;
    private String info2;
    private String info3;
    private String info4;
    private String info5;
    private String info6;
    private String info7;
    private String info8;
    private String info9;
    private String info10;
    private String info11;
    private List<CrmDeal> list1;
    private String text;


    public KeyValue() {
    }

    public KeyValue(String key, String value, String info) {
		super();
		this.key = key;
		this.value = value;
		this.info = info;
	}

	public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
		return info5;
	}

	public void setInfo5(String info5) {
		this.info5 = info5;
	}

	public String getInfo6() {
		return info6;
	}

	public void setInfo6(String info6) {
		this.info6 = info6;
	}

	public String getInfo7() {
		return info7;
	}

	public void setInfo7(String info7) {
		this.info7 = info7;
	}

	public String getInfo8() {
		return info8;
	}

	public void setInfo8(String info8) {
		this.info8 = info8;
	}

	public String getInfo9() {
		return info9;
	}

	public void setInfo9(String info9) {
		this.info9 = info9;
	}

	public String getInfo10() {
		return info10;
	}

	public void setInfo10(String info10) {
		this.info10 = info10;
	}

	public String getInfo11() {
		return info11;
	}

	public void setInfo11(String info11) {
		this.info11 = info11;
	}



	public List<CrmDeal> getList1() {
		return list1;
	}

	public void setList1(List<CrmDeal> list1) {
		this.list1 = list1;
	}

	public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}