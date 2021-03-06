<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" onclick="return hideSubMenu()">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel='icon' href='../favicon.ico' type='image/x-icon'>
    <link rel="stylesheet" href="../css/person_add_update.css">
    <title>Update Admin Details</title>
</head>

<body>
    <jsp:include page="../html-common/cms-header.jsp" />

    <div class="container">
        <jsp:include page="../html-common/cms-navigation.jsp" />
        <div class="details-container">
            <h1>${editAdmin.getRole()}'s details</h1>
            <a href="/user-list?type=${type}">&#60;- Back to the list</a>
            <form class="personal-details" action="/user/edit" method="post">
                <h2>Personal details</h2>
                <label for="person-name">Name*:</label>
                <input type="text" name="person-name" id="person-name" value="${editAdmin.getName()}">
                <label for="person-mail">Email*:</label>
                <input type="text" name="person-mail" id="person-mail" value="${editAdmin.getEmail()}">
                <label for="person-city">City*:</label>
                <input type="text" name="person-city" id="person-city" value="${editAdmin.getCity()}">
                <div class="lower-section">
                    <p>*- Fields marked like the need to be filled to add new entry</p>
                    <button class="btn" id="update-admin">Update</button>
                </div>
            </form>
        </div>
    </div>

    <jsp:include page="../html-common/footer.html" />
    <script>
        <%
        String permissions = (String) request.getAttribute("type");
        if(permissions.equals("admin")){
        %>
        document.getElementsByClassName('admins-nav')[0].setAttribute('id', 'select-page');
        <%
     }else{
     %>
        document.getElementsByClassName('mentors-nav')[0].setAttribute('id', 'select-page');
        <%
        }
        %>
    </script>
</body>

</html>