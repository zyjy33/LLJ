package com.hengyushop.demo.at;

import com.hengyushop.db.DBManager;


public class Common {
	//֧����
//	//�̻�PID
//	public static final String PARTNER = "2088811057210856";
//	//�̻��տ��˺�
//	public static final String SELLER = "szyunsen1@163.com";
//	//�̻�˽Կ��pkcs8��ʽ
//	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAOqwxrQuEihMXVPaEzvjzL0nfGplAZEB0hfkcf9gzIRFMwtbr+Rhljj+ntWbQfLqCjFABWsC/RL9VfKaW1mz+x07tYTDedL/MPXsifQxLrZ9vVDSApSTYJvO4VqZ/uJZTbC6i7WtFDShSLWjBcpb3OKejLS2HjzA5qF1qNZntNEZAgMBAAECgYEAtnuJpWQFPkxSbPat6e1wrstbFCdBlozB3U4Fzbpoi5h63iQGmh++/MYOnqzAFK8iCbVsAQ8r1G4jPCYFTbSCVXVs5rmB75WqGXu//btHucBtXpHle0uWQyqrXnZZPMJTP29sFztPMr6mUy6FKCaWeA0ihqRa+h2Ka9YaquOCJgECQQD5/ISeF/4JckoS7+a0U9TJkruguUbHHarWIZhjOS6yMnuTGSwGKUiIMzSicl+0pjP4WKCPZM9PX/eLhurZpVJhAkEA8FYP6yTB+3QQGshQXhJ9RK8Nzmii0w5J5bdnS66/a6pQs3EEak8dlSezZW5MPZ8VHZMjnrafNMGQs+npEoLpuQJBAKUICIDZ8/JGihJAX/ySDzrXbJhpWAlhU4OzgAeZG3O2khAFISQcIu8PZuMLQJVg15RO5ghkE9whzalF80qlsmECQQDc2ymbu9arPbgC9KuuFy2YrYlxcgSXER1lhUneacKsrQGmNKiLDRMxWx9niZl0UzlzSSDFnCrnry1LuBugED+5AkBGsxkquKKfgLAb9oxRrhwQwQ/c3hfg6ajkc48ixIPRtL3zivqtpU1WBH112yMgKjUbe8o2XKIQH+uK/4ATLJo6";
	
	public static final String PARTNER = "2088621723583186";
	                                      
	//�̻��տ��˺�
	public static final String SELLER = "lelinjukfpt@163.com";
	
	//�̻�˽Կ��pkcs8��ʽ
//	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKCR4UXYOQ2X6JWM41HsEcRn0Hdra6056zz/IN4+2C7aJlix85TSKTBge1g5eBlu2vGkX5WjY6dc8FH7sVt+HTlWMN2us0Mw/41c+6wuLw+w9UjLjgeA3J1ZnXPlJ2NaxmiQy6+oCpWmKIZJcj9n+oh3xqnaW62ZFYWDHvGp4983AgMBAAECgYABY6G2T/uWYYUSnihtUgSJVXiZP+Wrlv+xBGgNZC/Mn/iK9ecsl3DhyUPn7H9cgPgF9S0ah1JaZVRy8SGEsB+iltfFm8Fxv5WDWCoNo8hjVdZcEKxUUni83P4WqqizcmJgOZuEf7CetBSvPCZ5YwcCuY4hOutuMTpp/Q+O4rTTuQJBANaLvzLxfbRfyTKb1A4ZXEj20G6BBpWrO233h+KPN1QTUfS7XiMHySCfKyR/xQ+zqG7X/Cq7of53+iz72fDBVBUCQQC/mEX6JyGUb3z5btUJ8uHPEtTn7zTuU4uUh9NKqxZQ01I3C03QW5EPEZDygrcSHXL310wj0E4g423GUERNWz0bAkEAyYr78SJUoSY+PnGOcjQ2skBRqw1AH0d9C5+1ogpBwpRnsAx3sVa2BRQhFieE+mweNQpm08nxsrgIZ0ur/3PNxQJAeyXY9KzqPIpcfcbrT/W5hmvIRTvPfdBdSx7yhG/rbBAoLBEr9tykJFdaH0Y9fXtIRl0wJ2Tgkusbx/gXrtESOQJATRb7eSOWw5vN69xfnLoJu1B2Am6vhg+A7WUdeZhAXLpHjrOCsdjjSgj2FWchww/viCIhN0AwXztU1w7U14KDRA==";
	public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCW62249EZqrxQv0Sixvagzj8DnSLZFt+PyQjLUIay+GIv6sNpJUV6v2MOChkgIoKfkWDtlXR/SPwP4nnqhM38a5hBCmhdmmEWEF/MJ94mBSaj3NS1dctETwR1UMoPXqljAnehfCC41azccqVX4H0PXJmp3tBBVzDNf7XTStZ9zkkxIdCKQZyHUpyasJwUegG6sBFa4OH5DxdVX4L3CuRriyLRymGKve34H4N5n1ksQZRSJdTsHGJLwzCjHgqf6QnIZOp9gydTC2PTDtqxsLJXuV1lrLbIrp0EmN3dxIDozN7E2o5a4ZamCpkk4uqx5mOcTRikmzRmDuroGi5xn6fSBAgMBAAECggEASWXMYhQjHwVw+uRtNyw23oS+W31ocDXD3XFGiRarjXQgt9ZFAz1YoIr6YsNtywxfOtWSZXTJ4hWVl9dzamzCF1HmfI22bcYGoPyIzcMek8tAKqIpIvaRKltUKTu8FSoaqeriNT2kQ4SWNBFC5z5FXyCmzPq8qw15uuCgXU3Jc4nmKPwP31p9Rt2j/UVuGYUWYxyjZff3+8E5kECzTl+nf6tqOLE8nrWZomoWB8fABj9/x86yN3HcIrVz9f/kyH04bnhzir9Yrbh6hOOpBajZwIiCpJzHpTPmUVGFG4JEriluAz/NRe7UAsikLCXT4ciQh6B0LQzkOzRp9+n3plwsAQKBgQDXJXD9fXHY0RHokK192VXFNXVXcWUs89r3S6LbbmwQw8n2EQqvMGfSela4g2qynfmkWbpeSKdepI+eJLYxorQDPgMEcp/gT/zSPqxzGgape33cg0GuHJ2EZNRo/PQtBo2JlF83wBEn8SLAG7VM4Ol/MNebECveeSbtgB3ElUSRoQKBgQCzk9ehF0bBaLJqYTZOgFP0X/To2MccDAfFg77zrXSATKxOLMW6oQ7VCZQB8VI9jNs94V37QYF44q1FhqoIB3Zk7nA85Dq2x4XMW9ndXIKh3I4TQO+SaGeDagJr6ZhX/vhunTdta+hyGiWYWf2vyjCpB44pfC8vCnnMYL54dS424QKBgQDKGa7RQJTAYmzbNYTz0kQC09vdf3TWTGAlq5RZYpn7CaBfGNUbinIMwsVR4IlLLqDCZVNvm/o1KoqKuNu5Nqi43vjApdhPQ3QwH5WpLgqSDKJHkbkSEGlbY8gws2XAIga09X6YhUDYb2kAY0bk5+YPh9cRH5jGDLKnqZDNCJb34QKBgCe+CHK2XAphyVqRuRsWe34R3nnJwAcNAotL5O647JeYYGnMrtXm3wl23TKw3aqRidPgFJSOosDGKeLBMtAkFqevvVc8i2H4NjxL96Nt3dtEnIzWSi70vDPxOif3wqd23jRcxu+IeiEGeFuHTwWgkbRW37d+rFKU1u/MWVr/VAMBAoGBAKgiQfDOKifm5XsGtmNLp+MFulwZIDTDEkg1F7FgVG4ZpauCkgnepcFbDGyT6Visi1SqCTvcxgluj1CLQRB5dA8N+8L6P2p/5/alYAPFWy6dXUJ14lENGjnER6M090yYpPNQUPh75fIIbLenY0I2PG9uCa1rbnG4XKI88re0Ekkk";
	
	//֧������Կ
//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDqsMa0LhIoTF1T2hM748y9J3xqZQGRAdIX5HH/YMyERTMLW6/kYZY4/p7Vm0Hy6goxQAVrAv0S/VXymltZs/sdO7WEw3nS/zD17In0MS62fb1Q0gKUk2CbzuFamf7iWU2wuou1rRQ0oUi1owXKW9zinoy0th48wOahdajWZ7TRGQIDAQAB";
	public static final String RSA_PUBLIC = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAluttuPRGaq8UL9Eosb2oM4/A50i2Rbfj8kIy1CGsvhiL+rDaSVFer9jDgoZICKCn5Fg7ZV0f0j8D+J56oTN/GuYQQpoXZphFhBfzCfeJgUmo9zUtXXLRE8EdVDKD16pYwJ3oXwguNWs3HKlV+B9D1yZqd7QQVcwzX+100rWfc5JMSHQikGch1KcmrCcFHoBurARWuDh+Q8XVV+C9wrka4si0cphir3t+B+DeZ9ZLEGUUiXU7BxiS8Mwox4Kn+kJyGTqfYMnUwtj0w7asbCyV7ldZay2yK6dBJjd3cSA6MzexNqOWuGWpgqZJOLqseZjnE0YpJs0Zg7q6BoucZ+n0gQIDAQAB";
	
	//
	public static String IMEI;
	public static String locationName = "locals";
	public static String TRAIN_SECKEY = "3e8d9caee01c485fd5414edee3d49660";
	public static String TRAIN_USERID = "szyunsen@163.com";
	/**
	 * ��λ�������ļ�
	 */
	public static final String DB_NAME = "tuangou.db";
	/**
	 * �Ź���ַ
	 */
	public static String TUAN_PATH = "/data/data/" + DBManager.PACKAGE_NAME
			+ "/databases/" + Common.DB_NAME;
	 
	
	//Я�̾Ƶ�
	public static String XIECHENG_KEY = "17FDBE73-0D17-40B3-A889-E90388E11C75";
	public static String ULR_KEY = "443386";
	public static String USER_KEY = "17562";
	public static int PAGER_LENGTH = 0;
}