<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${cookie.language.value == null}">
	<jstl:set value="true" var="inEnglish" />
</jstl:if>

<jstl:if test="${cookie.language.value == 'es'}">
	<jstl:set value="false" var="inEnglish" />
</jstl:if>

<jstl:if test="${cookie.language.value == 'en'}">
	<jstl:set value="true" var="inEnglish" />
</jstl:if>

<form:form action="${requestURI}" modelAttribute="chorbi">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.username" />
	
	<p><spring:message code="chorbi.useraccount.username"/>: <jstl:out value="${chorbi.userAccount.username}" /></p> 

    <acme:password code="chorbi.useraccount.password" path="userAccount.password"/>
    
	<acme:textbox code="chorbi.name" path="name"/>
	
	<acme:textbox code="chorbi.surname" path="surname"/>
	
	<acme:textbox code="chorbi.email" path="email"/>
	
	<acme:textbox code="chorbi.phoneNumber" path="phoneNumber"/>
	
	<acme:textbox code="chorbi.picture" path="picture"/>
	
	<acme:textbox code="chorbi.description" path="description"/>
	
	<form:select path="relationshipType">
		<option value="ACTIVITIES" <jstl:if test="${chorbi.relationshipType == 'ACTIVITIES'}">selected = "selected"</jstl:if>>
			<jstl:choose>
				<jstl:when test="${inEnglish}">
					ACTIVITIES
				</jstl:when>
				<jstl:otherwise>
					ACTIVIDADES
				</jstl:otherwise>
			</jstl:choose>
		</option>
		<option value="FRIENDSHIP" <jstl:if test="${chorbi.relationshipType == 'FRIENDSHIP'}">selected = "selected"</jstl:if>>
			<jstl:choose>
				<jstl:when test="${inEnglish}" >
					FRIENDSHIP
				</jstl:when>
				<jstl:otherwise>
					AMISTAD
				</jstl:otherwise>
			</jstl:choose>
		</option>
		<option value="LOVE" <jstl:if test="${chorbi.relationshipType == 'LOVE'}">selected = "selected"</jstl:if>>
			<jstl:choose>
				<jstl:when test="${inEnglish}" >
					LOVE
				</jstl:when>
				<jstl:otherwise>
					AMOR
				</jstl:otherwise>
			</jstl:choose>
		</option>
	</form:select>
	
	<acme:textbox code="chorbi.birthDate" path="birthDate"/>
	
	<form:select path="genre">
		<option value="MAN" <jstl:if test="${chorbi.genre == 'MAN'}">selected = "selected"</jstl:if>>
			<jstl:choose>
				<jstl:when test="${inEnglish}">
					MAN
				</jstl:when>
				<jstl:otherwise>
					HOMBRE
				</jstl:otherwise>
			</jstl:choose>
		</option>
		<option value="WOMAN" <jstl:if test="${chorbi.genre == 'WOMAN'}">selected = "selected"</jstl:if>>
			<jstl:choose>
				<jstl:when test="${inEnglish}">
					WOMAN
				</jstl:when>
				<jstl:otherwise>
					MUJER
				</jstl:otherwise>
			</jstl:choose>
		</option>
	</form:select>
	
	<acme:textbox code="chorbi.country" path="country"/>
	
	<acme:textbox code="chorbi.state" path="state"/>
	
	<acme:textbox code="chorbi.province" path="province"/>
	
	<acme:textbox code="chorbi.city" path="city"/>
	
	<br/>
	
	<button type="submit" name="save" class="btn btn-primary" id="save">
		<spring:message code="chorbi.save" />
	</button>
	
	<acme:cancel url="welcome/index.do" code="chorbi.cancel"/>
	
	
</form:form>
