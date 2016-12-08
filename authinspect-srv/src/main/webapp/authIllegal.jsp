<%--
  Created by IntelliJ IDEA.
  User: coolAutumn
  Date: 3/30/16
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>验证页面</title>

    <!--jquery+bootstrap框架引用-->
    <link href="<%=basepath%>css/bootstrap.css" rel="stylesheet" />
    <script src="<%=basepath%>js/jquery-1.10.2.min.js"></script>
    <script src="<%=basepath%>js/bootstrap.min.js"></script>
    <link href="<%=basepath%>css/login.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="" id="login-wrapper">

        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div id="logo-login">
                    <h1>请向软件开发商验证
                    </h1>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="account-box">
                    <form action="authoritycheck">
                        <div class="form-group">
                            <!--a href="#" class="pull-right label-forgot">Forgot email?</a-->
                            <label >所给密报:</label>
                            <input type="text" name="serial" id="serial" class="form-control" readonly value="<%=application.getAttribute("serial")%>" >
                        </div>
                        <div class="form-group">
                            <!--a href="#" class="pull-right label-forgot">Forgot password?</a-->
                            <label >开发商所给匹配码:</label>
                            <input type="text" name="matchcode" id="matchcode" class="form-control">
                        </div>
                        <div align="center">
                            <button class="btn btn-primary" type="submit" >
                                验证
                            </button>
                            <label>开发商联系方式：武汉大学国际软件学院 涂建光 </label>
                            <label>QQ:   374821764</label>
                            <label>Tele: 13971194546</label>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
