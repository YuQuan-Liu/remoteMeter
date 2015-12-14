package com.xdkj.yccb.main.infoin.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Meterkind;
import com.xdkj.yccb.main.entity.Pricekind;

public class MeterView {
	private Integer pid;
	private int c_id;
	private String pk;
	private String pk_id;
	private String gprs;
	private String gprs_id;
	private String mk;
	private String mk_id;
	private String apid;
	private String steelNum;
	private String qfh;
	private String collectorAddr;
	private String meterAddr;
	private byte meterSolid;
	private char lihu;
	private int mainMeter;
	private int suppleMode;
	private int isValve;
	private Byte valveState;
	private Integer deductionStyle;
	private Integer valveOffthre;
	private Byte meterState;
	private Integer deRead;
	private String deTime;
	private Integer destartread;
	private String destarttime;
	private int timerSwitch;
	private String timer;
	private Integer overflow;
	private Integer changend;
	private Integer changestart;
	private int readdata;
	private String readtime;
//	private char valid;
//	private String remark;
	
	public Integer getPid() {
		return pid;
	}
	public int getReaddata() {
		return readdata;
	}
	public void setReaddata(int readdata) {
		this.readdata = readdata;
	}
	public String getReadtime() {
		return readtime;
	}
	public void setReadtime(String readtime) {
		this.readtime = readtime;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getPk_id() {
		return pk_id;
	}
	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}
	public String getGprs_id() {
		return gprs_id;
	}
	public void setGprs_id(String gprs_id) {
		this.gprs_id = gprs_id;
	}
	public String getMk_id() {
		return mk_id;
	}
	public void setMk_id(String mk_id) {
		this.mk_id = mk_id;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getGprs() {
		return gprs;
	}
	public void setGprs(String gprs) {
		this.gprs = gprs;
	}
	public String getMk() {
		return mk;
	}
	public void setMk(String mk) {
		this.mk = mk;
	}
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	public String getSteelNum() {
		return steelNum;
	}
	public void setSteelNum(String steelNum) {
		this.steelNum = steelNum;
	}
	public String getQfh() {
		return qfh;
	}
	public void setQfh(String qfh) {
		this.qfh = qfh;
	}
	public String getCollectorAddr() {
		return collectorAddr;
	}
	public void setCollectorAddr(String collectorAddr) {
		this.collectorAddr = collectorAddr;
	}
	public String getMeterAddr() {
		return meterAddr;
	}
	public void setMeterAddr(String meterAddr) {
		this.meterAddr = meterAddr;
	}
	public byte getMeterSolid() {
		return meterSolid;
	}
	public void setMeterSolid(byte meterSolid) {
		this.meterSolid = meterSolid;
	}
	public char getLihu() {
		return lihu;
	}
	public void setLihu(char lihu) {
		this.lihu = lihu;
	}
	public int getMainMeter() {
		return mainMeter;
	}
	public void setMainMeter(int mainMeter) {
		this.mainMeter = mainMeter;
	}
	public int getSuppleMode() {
		return suppleMode;
	}
	public void setSuppleMode(int suppleMode) {
		this.suppleMode = suppleMode;
	}
	public int getIsValve() {
		return isValve;
	}
	public void setIsValve(int isValve) {
		this.isValve = isValve;
	}
	public Byte getValveState() {
		return valveState;
	}
	public void setValveState(Byte valveState) {
		this.valveState = valveState;
	}
	public Integer getDeductionStyle() {
		return deductionStyle;
	}
	public void setDeductionStyle(Integer deductionStyle) {
		this.deductionStyle = deductionStyle;
	}
	public Integer getValveOffthre() {
		return valveOffthre;
	}
	public void setValveOffthre(Integer valveOffthre) {
		this.valveOffthre = valveOffthre;
	}
	public Byte getMeterState() {
		return meterState;
	}
	public void setMeterState(Byte meterState) {
		this.meterState = meterState;
	}
	public Integer getDeRead() {
		return deRead;
	}
	public void setDeRead(Integer deRead) {
		this.deRead = deRead;
	}
	public String getDeTime() {
		return deTime;
	}
	public void setDeTime(String deTime) {
		this.deTime = deTime;
	}
	public int getTimerSwitch() {
		return timerSwitch;
	}
	public void setTimerSwitch(int timerSwitch) {
		this.timerSwitch = timerSwitch;
	}
	public String getTimer() {
		return timer;
	}
	public void setTimer(String timer) {
		this.timer = timer;
	}
	public Integer getOverflow() {
		return overflow;
	}
	public void setOverflow(Integer overflow) {
		this.overflow = overflow;
	}
	public Integer getChangend() {
		return changend;
	}
	public void setChangend(Integer changend) {
		this.changend = changend;
	}
	public Integer getChangestart() {
		return changestart;
	}
	public void setChangestart(Integer changestart) {
		this.changestart = changestart;
	}
//	public char getValid() {
//		return valid;
//	}
//	public void setValid(char valid) {
//		this.valid = valid;
//	}
//	public String getRemark() {
//		return remark;
//	}
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
	public MeterView() {
		super();
	}
	@Override
	public String toString() {
		return "MeterView [pid=" + pid + ", pk=" + pk + ", pk_id=" + pk_id
				+ ", gprs=" + gprs + ", gprs_id=" + gprs_id + ", mk=" + mk
				+ ", mk_id=" + mk_id + ", apid=" + apid + ", steelNum="
				+ steelNum + ", qfh=" + qfh + ", collectorAddr="
				+ collectorAddr + ", meterAddr=" + meterAddr + ", meterSolid="
				+ meterSolid + ", lihu=" + lihu + ", mainMeter=" + mainMeter
				+ ", suppleMode=" + suppleMode + ", isValve=" + isValve
				+ ", valveState=" + valveState + ", deductionStyle="
				+ deductionStyle + ", valveOffthre=" + valveOffthre
				+ ", meterState=" + meterState + ", deRead=" + deRead
				+ ", deTime=" + deTime + ", timerSwitch=" + timerSwitch
				+ ", timer=" + timer + ", overflow=" + overflow + ", changend="
				+ changend + ", changestart=" + changestart + "]";
	}
	public Map<String, String> check_view() {
		HashMap<String, String> result = new HashMap<>();
		result.put("success", "true");
		
		return result;
	}
	public Integer getDestartread() {
		return destartread;
	}
	public void setDestartread(Integer destartread) {
		this.destartread = destartread;
	}
	public String getDestarttime() {
		return destarttime;
	}
	public void setDestarttime(String destarttime) {
		this.destarttime = destarttime;
	}

	
	
}
