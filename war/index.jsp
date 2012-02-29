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
        String s;
        for (Entity exercise : exercises) {
            s = "failed";
            if(exercise.getProperty("passedtests") == exercise.getProperty("totaltests")){
                s = "solved";
            }
        %>
			<p><%= exercise.getProperty("date") %> &mdash; <b><%= exercise.getProperty("username") %></b> <%= s %> <em><%= exercise.getProperty("exoname")%></em> in <u><%= exercise.getProperty("exolang")%></u>.</p>
		<%
        }
    }
%></body>
</html>