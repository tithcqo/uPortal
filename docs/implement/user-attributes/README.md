# User Attributes

This section describes how to configure your own sources of user attributes and integrate them into
uPortal.

## First Time User Logins

:warning: The following three attributes are commonly used within uPortal and Apereo portlets:

 * `uid`
 * `username`
 * `user.login.id`

The default (starting) configuration will provide these attributes for all users through the
`uPortalJdbcUserSource` bean. This bean, however, **only recognizes users who have logged into the
portal at least once** or who have been imported via Import/Export. It is **very important to map
these same 3 attributes** to an external source, such as LDAP or SAML.

## Configuring User Attribute Data Sources

The basic class for a uPotal user is an implementation of the `IPerson` interface. The _uPortal
Person Directory Service_ is used to populate and retrieve user attributes.  Person Directory is
maintained as an [independent project with its own source code called PersonDirectory][].
Attributes can be acquired from multiple sources via LDAP, JDBC, or other sources as required.

The Person Directory subsystem is based on concrete implementations of the `IPersonAttributeDao`
interface.  These objects are Spring-managed beans.  uPortal 5 comes pre-configured with several
instances of `IPersonAttributeDao`, but the most interesting (and most important!) sources of user
attribute information are the ones you provide yourself.

Add your user attribute sources to uPortal by configuring beans that implement `IPersonAttributeDao`
and adding them to the Spring Application Context.  uPortal will find the beans you declare and add
them to the User Attributes subsystem appropriately.

There are several ways to add beans to the uPortal Application Context using [uPortal-start][].  One
of the more common ways is to declare them in a Spring XML configuration file within the
`overlays/uPortal/src/main/resources/properties/contextOverrides/' directory.

### Example `IPersonAttributeDao` Bean Definition

```xml
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="
           http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.1.xsd">

    <bean id="layoutNodesCountPersonAttributeDao" class="org.apereo.services.persondir.support.jdbc.SingleRowJdbcPersonAttributeDao">
        <constructor-arg index="0" ref="PersonDB" />
        <constructor-arg>
            <value>
                select count(*) as layoutNodesCount
                    from up_layout_struct
                    where user_id = (select user_id
                        from up_user
                        where {0})
            </value>
        </constructor-arg>
        <property name="usernameAttributeProvider" ref="usernameAttributeProvider" />
        <property name="queryAttributeMapping">
            <map>
                <entry key="username" value="USER_NAME" />
            </map>
        </property>
        <property name="resultAttributeMapping">
            <map>
                <entry key="layoutNodesCount">
                    <set>
                        <value>layoutNodesCount</value>
                    </set>
                </entry>
            </map>
        </property>
    </bean>

</beans>
```

## User Attribute Data Sources

 * [LDAP User Attribute Sources](ldap.md)
 * [JDBC User Attribute Sources](jdbc.md)

[independent project with its own source code called PersonDirectory]: https://github.com/apereo/person-directory
[uPortal-start]: https://github.com/Jasig/uPortal-start
