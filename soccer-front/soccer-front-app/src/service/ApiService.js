export const SPRING_SERVER_URL = process.env.REACT_APP_SPRING_SERVER_URL;

export function getAccessToken() {
    const cookies = document.cookie.split(';').map(cookie => cookie.trim());
    const accessTokenCookie = cookies.find(cookie => cookie.startsWith("accessToken="));
    return accessTokenCookie ? accessTokenCookie.split("=")[1] : null;
}

export function logout() {
    document.cookie =  "accessToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}