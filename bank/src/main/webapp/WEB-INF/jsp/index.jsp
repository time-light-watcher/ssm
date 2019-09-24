<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>个人网上银行——登录</title>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">

    function login() {
        var checkCardNumber = /^\d{16}$/;
        var checkPassword = /^\d{6}$/;

        var $cardNumber = $("#cardNumber");
        var $password = $("#password");

        if (!checkCardNumber.test($cardNumber.val())) {
            alert("请正确填写您的16位卡号");
        } else if (!checkPassword.test($password.val())) {
            alert("请正确填写6位登录密码");
        } else {
            var params = $("#loginForm").serialize();
            $.post("${pageContext.request.contextPath}/user/login", params, function (data) {
                if (data === 'success') {
                    location.href = "${pageContext.request.contextPath}/user/bankMain";
                } else if (data === 'noCardNumber') {
                    alert("登录失败！您输入的卡号不存在！");
                }else if (data === 'errorPassword') {
                    alert("登录失败！密码错误！");
                }else if (data === 'userFrozen') {
                    alert("登录失败！账户已冻结！");
                }
            });
        }
        return false;
    }

</script>
<body>
<form id="loginForm">
    <table>
        <tr>
            <th colspan="2">
                个人网上银行
            </th>
        </tr>
        <tr>
            <td>卡号：</td>
            <td>
                <input type="text" name="cardNumber" id="cardNumber">
            </td>
        </tr>
        <tr>
            <td>密码：</td>
            <td>
                <input type="password" name="password" id="password">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="登录网上银行" onclick="return login()">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
