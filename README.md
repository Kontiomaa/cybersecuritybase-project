# cybersecuritybase-project

## Info

This project is part of the course series "Cyber Security Base with F-Secure".

Info on the course series:
http://mooc.fi/courses/2016/cybersecurity/

Info on the task:
https://cybersecuritybase.github.io/project/

Basically we were tasked to create a web application from a template that has at least five different flaws from the OWASP top ten list (https://www.owasp.org/index.php/Top_10_2013-Top_10). Explain how the flaws can be found and fixed.

## Flaw A3-Cross-Site Scripting (XSS)

### Problem

The form (http://localhost:8080/form) allows scripts to be saved my malicious users and later run by other users (administrators) at the admin page (http://localhost:8080/admin).

### Fix

To fix the script execution at the page "http://localhost:8080/admin": in the file registerList.html change the "th:utext" attributes to "th:text".

## Flaw A5-Security Misconfiguration

### Problem

In SecurityConfiguration.java the line " http.authorizeRequests().anyRequest().permitAll(); " allows an attacker access the admin page (http://localhost:8080/admin) without authentication.

### Fix

By replacing the line " http.authorizeRequests().anyRequest().permitAll(); " with " http.authorizeRequests().antMatchers("/","/form","/auth").permitAll().anyRequest().authenticated(); " it tells spring that the website's root and pages "form" and "auth" are accessible without authentication.

## Flaw A6-Sensitive Data Exposure

### Problem

When logging in on a HTTP connection the username and password can be intercepted by a man in the middle attack.

### Fix

By enabling HTTPS connection on the whole site, the login data will be sent securely.

Do the following: under src\main\resources rename the file "application.properties.txt" to "application.properties". The file contains information to the application on how to use ssl and information on the certificate (instead of the port 8080 now use 8443). I have created a self signed certificate called keystore.p12 on the root of the project which will then be used. You can create your own key (at least on Windows) with the following command from your java installation's bin folder: (I ran it from C:\Program Files\Java\jdk1.8.0_111\bin) "keytool -genkey -alias mykey -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650".

## Flaw A8-Cross-Site Request Forgery (CSRF)

### Problem

A malicious user could send data in secret on behalf of an unknowing user when he or she enters a compromized website or a website the malicious user controls. For simplicity I've added a link to the form page which leads to evilForm.html. The evil form will send a form in secret with the text "EVIL" in every field. The "damage" can be seen at the admin page.

### Fix

By adding the following input to the form in form.html "<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>" (or uncomment the one I left) a token is added to every submit thus preventing cross-site requests. To enable token checking, comment the row "http.csrf().disable();" in securityConfiguration.java.
