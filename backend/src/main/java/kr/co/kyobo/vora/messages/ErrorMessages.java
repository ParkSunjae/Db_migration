package kr.co.kyobo.vora.messages;

public enum ErrorMessages {
    SYSTEM_COMMON_FAIL("SYSTEM_COMMON_FAIL","system.common.fail"),
    SYSTEM_COMMON_INVALID_REQUEST("SYSTEM_COMMON_INVALID_REQUEST", "system.common.invalid.request"),
    SYSTEM_DB_INSERT_FAIL("SYSTEM_DB_INSERT_FAIL","system.db.insert.fail"),
    SYSTEM_DB_UPDATE_FAIL("SYSTEM_DB_UPDATE_FAIL","system.db.update.fail"),
    SYSTEM_DB_FIND_FAIL("SYSTEM_DB_FIND_FAIL","system.db.find.fail"),
    SYSTEM_DB_DELETE_FAIL("SYSTEM_DB_DELETE_FAIL","system.db.delete.fail"),
    COMMON_LOGIN_FAIL("COMMON_LOGIN_FAIL","common.login.fail"),
    COMMON_LOGIN_FAIL_EXPIRE_CERT_NUM("COMMON_LOGIN_FAIL_EXPIRE_CERT_NUM","common.login.fail.expire.cert.num"),
    COMMON_UNAUTHORIZED_FAIL("COMMON_UNAUTHORIZED_FAIL","common.unauthorized.fail"),
    COMMON_JOIN_DUPLICATION_FAIL("COMMON_JOIN_DUPLICATION_FAIL","common.join.duplication.fail"),
    WEB_ACCESS_DENIED("WEB_ACCESS_DENIED", "web.access.deniend"),
    ACCOUNT_DAILY_LOOKUP_LIMIT_EXCEED("ACCOUNT_DAILY_LOOKUP_LIMIT_EXCEED", "account.daily.lookup.limit.exceed"),
    NOT_JOINDED_USER("NOT_JOINDED_USER", "not.joinded.user"),
    ALREADY_REQUESTED_REFUND("ALREADY_REQUESTED_REFUND", "already.requested.refund"),
    ALREADY_REQUESTED_CANCEL("ALREADY_REQUESTED_CANCEL", "already.requested.cancel"),
    STOLEN_NICKNAME("STOLEN_NICKNAME","stolen.nickname");

    ErrorMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessage(String code) {
        for ( ErrorMessages ecc : ErrorMessages.values()){
            if(ecc.getCode().equals(code)){
                return ecc.getMessage();
            }
        }
        return "";
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
