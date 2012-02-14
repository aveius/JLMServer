<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<p>Hello, look at JLM results:</p>

<%
    // Get the Datastore Service
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // Query all exercises
    Query query = new Query("Exercise");
    List<Entity> exercises = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    if (exercises.isEmpty()) {
        %>
        <p>There are not yet exercises done...</p>
        <%
    } else {
        for (Entity exercise : exercises) {
        %>
    <p><b><%= exercise.getProperty("username") %></b> worked.</p>
<%
        }
    }
%></body>
</html>