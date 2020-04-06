<Context>
    <Resource name="jdbc/webAppMySQL"
              auth="Container"
              type="javax.sql.DataSource"
              maxActive="100" maxIdle="30" maxWait="10000"
              username="root" password="Frfgekmrj391"
              driverClassName="com.mysql.cj.jdbc.Driver"
              defaultAutoCommit="false"
              defaultTransactionIsolation="READ_COMMITTED"
              connectionProperties="useUnicode=yes;characterEncoding=utf8;"
              url="jdbc:mysql://localhost:3306/webAppMySQL"/>

</Context>

<%--NOT sure about localhost--%>