<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authentication property="principal" var ="loggedactor"/>
<jstl:set var="chorbi" value="${chorbi}"/> 

<h2><spring:message code="chorbi" /></h2>
<p><spring:message code="chorbi.name"/>: <jstl:out value="${chorbi.name}" /></p> 
<p><spring:message code="chorbi.surname"/>: <jstl:out value="${chorbi.surname}" /></p> 
<p><spring:message code="chorbi.email"/>: <jstl:out value="${chorbi.email}" /></p> 
<p><spring:message code="chorbi.phone"/>: <jstl:out value="${chorbi.phone}" /></p> 

<img src="<jstl:out value='${chorbi.picture}'/>"  width="300"> 

<p><spring:message code="chorbi.description"/>: <jstl:out value="${chorbi.description}" /></p> 
<p><spring:message code="chorbi.relationshipType"/>: <jstl:out value="${chorbi.relationshipType}" /></p> 
<p><spring:message code="chorbi.birthDate"/>: <jstl:out value="${chorbi.birthDate}" /></p> 
<p><spring:message code="chorbi.chorbi.genre"/>: <jstl:out value="${chorbi.chorbi.genre}" /></p>  
<p><spring:message code="chorbi.country"/>: <jstl:out value="${chorbi.country}" /></p> 
<p><spring:message code="chorbi.state"/>: <jstl:out value="${chorbi.state}" /></p>  
<p><spring:message code="chorbi.province"/>: <jstl:out value="${chorbi.province}" /></p> 
<p><spring:message code="chorbi.city"/>: <jstl:out value="${chorbi.city}" /></p> 


<br/>

<security:authorize access="hasRole('CUSTOMER')">
	<jstl:if test="${chorbi.userAccount.username==loggedactor.username}">
		<a href="chorbi/chorbi/edit.do?"> <spring:message code="chorbi.edit" /></a>
	</jstl:if>
</security:authorize>

<br/>

