<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>个人网上银行</title>
</head>
<script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">

    function logout() {
        var b = confirm("确定要退出网上银行吗？");
        if (b) {
            location.href = "${pageContext.request.contextPath}/user/logout";
        }
    }

    function showMoney() {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/showMoney",
            success: function (data) {
                if (data !== null) {
                    $("#showSpace").html(
                        "<h1>您的账户余额是：</h1>" +
                        "<h3>&emsp;&emsp;&emsp;&emsp;人民币：&emsp;&emsp;&emsp;&emsp;" + data + "元</h3>"
                    );
                }
            }
        });
    }

    function transferView() {
        $("#showSpace").html(
            "<h1>当前操作：转账。请输入转入账号和金额后点“转账”按钮</h1>" +
            "<form id='transferForm'>" +
            "    <table style='margin: auto;text-align: center'>" +
            "        <tr>" +
            "            <td>转入账号：</td>" +
            "            <td>" +
            "                <input type='text' name='cardNumberTo' id='cardNumberTo'>" +
            "            </td>" +
            "        </tr>" +
            "        <tr>" +
            "            <td>转账金额：</td>" +
            "            <td>" +
            "                <input type='text' name='moneyTransfer' id='moneyTransfer'>" +
            "            </td>" +
            "        </tr>" +
            "        <tr>" +
            "            <td colspan='2'>" +
            "                <input type='submit' value='转账' onclick='return doTransfer()'>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "</form>"
        );
    }

    function doTransfer() {
        var checkCardNumber = /^\d{16}$/;
        var checkMoney = /^\d+(\.\d{0,2})?$/;

        var $cardNumberTo = $("#cardNumberTo");
        var $moneyTransfer = $("#moneyTransfer");

        if (!checkCardNumber.test($cardNumberTo.val())) {
            alert("请正确填写要转入的16位卡号");
        } else if (!checkMoney.test($moneyTransfer.val())) {
            alert("请正确填写转账金额");
        } else {
            var params = $("#transferForm").serialize();
            $.post("${pageContext.request.contextPath}/user/transfer", params, function (data) {
                if (data === 'success') {
                    $("#showSpace").html(
                        "<h1>操作已成功！请继续选择其它服务</h1>"
                    );
                } else if (data === 'noUserTo') {
                    alert("转账失败！目标账户不存在！");
                } else if (data === 'userToFrozen') {
                    alert("转账失败！目标账户已冻结！")
                } else if (data === 'moneyNotEnough') {
                    alert("转账失败！转出账户金额不足！")
                } else {
                    alert("未知错误！请稍后重试")
                }
            });
        }
        return false;
    }

    function logView() {
        $("#showSpace").html(
            "<h1>当前操作：查询交易记录。请选择时间范围后点“查询”按钮</h1>" +
            "<form id='logForm'>" +
            "    <table style='margin: auto;text-align: center'>" +
            "        <tr>" +
            "            <td>查询日期范围：</td>" +
            "            <td>" +
            "                <input type='date' name='dateFrom' id='dateFrom'>" +
            "            </td>" +
            "            <td>到：</td>" +
            "            <td>" +
            "                <input type='date' name='dateTo' id='dateTo'>" +
            "            </td>" +
            "            <td>" +
            "                <input type='submit' value='查询' onclick='return showLog()'>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "</form>" +
            "<table id='result' style='text-align: center;width: 100%;border-collapse: collapse'>" +
            "</table>"
        );
    }

    function showLog(page) {
        var params = $("#logForm").serialize();
        if (page != null) {
            params = params + "&page=" + page;
        }

        $.post("${pageContext.request.contextPath}/log/showLog", params, function (logPage) {
            $("#result").html(
                "<tr>" +
                "    <th style='border-style: solid'>交易日期</th>" +
                "    <th style='border-style: solid'>支出</th>" +
                "    <th style='border-style: solid'>存入</th>" +
                "    <th style='border-style: solid'>账户余额</th>" +
                "    <th style='border-style: solid'>交易类型</th>" +
                "    <th style='border-style: solid'>备注</th>" +
                "</tr>"
            );
            for (var log of logPage.rows) {
                if (log.moneyIn === null) {
                    log.moneyIn = "";
                }
                if (log.moneyOut === null) {
                    log.moneyOut = "";
                }
                if (log.comment === null) {
                    log.comment = "";
                }
                $("#result").append(
                    "<tr>" +
                    "    <td style='border-style: solid'>" + log.date + "</td>" +
                    "    <td style='border-style: solid'>" + log.moneyOut + "</td>" +
                    "    <td style='border-style: solid'>" + log.moneyIn + "</td>" +
                    "    <td style='border-style: solid'>" + log.result + "</td>" +
                    "    <td style='border-style: solid'>" + log.type + "</td>" +
                    "    <td style='border-style: solid'>" + log.comment + "</td>" +
                    "</tr>"
                );
            }

            var pageUp = logPage.page > 1 ? logPage.page - 1 : 1;
            var pageDown = logPage.page < logPage.totalPage ? logPage.page + 1 : logPage.totalPage;

            $("#result").append(
                "<tr>" +
                "   <td colspan='6' style='border-style: solid'>" +
                "       <a id='homePage' href='javascript:;' onclick='showLog(1)'>首页</a>|" +
                "       <a id='pageUp' href='javascript:;' onclick='showLog(" + pageUp + ")'>上一页</a>|" +
                "       <a id='pageDown' href='javascript:;' onclick='showLog(" + pageDown + ")'>下一页</a>|" +
                "       <a id='lastPage' href='javascript:;' onclick='showLog(" + logPage.totalPage + ")'>末页</a>&emsp;" +
                "       第<span id='page'>" + logPage.page + "</span>页/" +
                "       共<span id='totalPage'>" + logPage.totalPage + "</span>页" +
                "       (<span id='totalCount'>" + logPage.totalCount + "</span>条记录)" +
                "   </td>" +
                "</tr>"
            );
        });
        return false;
    }

    function changePasswordView() {
        $("#showSpace").html(
            "<h1>当前操作：修改密码。请按要求填写完整后点“修改”按钮</h1>" +
            "<form id='changePasswordForm'>" +
            "    <table style='margin: auto;text-align: center'>" +
            "        <tr>" +
            "            <td>请输入现在的密码：</td>" +
            "            <td>" +
            "                <input type='password' name='passwordOld' id='passwordOld'>" +
            "            </td>" +
            "        </tr>" +
            "        <tr>" +
            "            <td>请输入新密码：</td>" +
            "            <td>" +
            "                <input type='password' name='passwordNew' id='passwordNew'>" +
            "            </td>" +
            "        </tr>" +
            "        <tr>" +
            "            <td>请再次输入新密码：</td>" +
            "            <td>" +
            "                <input type='password' name='passwordNewCheck' id='passwordNewCheck'>" +
            "            </td>" +
            "        </tr>" +
            "        <tr>" +
            "            <td colspan='2'>" +
            "                <input type='submit' value='修改' onclick='return doChangePassword()'>" +
            "            </td>" +
            "        </tr>" +
            "    </table>" +
            "</form>"
        );
    }

    function doChangePassword() {
        var checkPassword = /^\d{6}$/;

        var passwordOld = $("#passwordOld").val();
        var passwordNew = $("#passwordNew").val();
        var passwordNewCheck = $("#passwordNewCheck").val();


        if ("" === passwordOld || "" === passwordNew || "" === passwordNewCheck) {
            alert("请将表单填写完整");
        } else if (!checkPassword.test(passwordOld) || !checkPassword.test(passwordNew) || !checkPassword.test(passwordNewCheck)) {
            alert("密码只能是6位数字");
        } else if (passwordNew !== passwordNewCheck) {
            alert("两次输入的新密码不相同");
        } else {
            var params = $("#changePasswordForm").serialize();
            $.post("${pageContext.request.contextPath}/user/changePassword", params, function (data) {
                if (data === 'success') {
                    $("#showSpace").html(
                        "<h1>操作已成功！请继续选择其它服务</h1>"
                    );
                } else if (data === 'passwordOldFalse') {
                    alert("旧密码输入错误");
                } else {
                    alert("未知错误！请稍后重试");
                }
            });
        }
        return false;
    }

</script>
<body>
<table style="width: 100%;height: 100%">
    <tr style="height: 5%">
        <td colspan="2">
            卡号：${sessionScope.user.cardNumber}
            <a href="javascript:;" onclick="logout()">退出登录</a>
        </td>
    </tr>
    <tr style="height: 5%">
        <td style="width: 20%"></td>
        <td id="showSpace" rowspan="6" style="width: 80%;border-style: solid;text-align: center">
            <h1>欢迎使用个人网上银行</h1>
        </td>
    </tr>
    <tr style="height: 5%">
        <td>
            <a href="javascript:;" onclick="showMoney()">查询余额</a>
        </td>
    </tr>
    <tr style="height: 5%">
        <td>
            <a href="javascript:;" onclick="transferView()">转账</a>
        </td>
    </tr>
    <tr style="height: 5%">
        <td>
            <a href="javascript:;" onclick="logView()">查询交易记录</a>
        </td>
    </tr>
    <tr style="height: 5%">
        <td>
            <a href="javascript:;" onclick="changePasswordView()">修改密码</a>
        </td>
    </tr>
    <tr style="height: 70%">
    </tr>
</table>
</body>
</html>
