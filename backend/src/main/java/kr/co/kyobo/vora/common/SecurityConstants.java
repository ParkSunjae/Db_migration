package kr.co.kyobo.vora.common;

public class SecurityConstants {
    public static final long EXPIRATION_SECONDES = 30 * 24 * 60 * 60; //90 day
    public static final String SECRET = "702843615885cd12fe6ddce9108eef31c3117031814425b156faa60a164491c7";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String USER_CP ="userCP";
    public static final String AUTH_CD ="authCD";
}
