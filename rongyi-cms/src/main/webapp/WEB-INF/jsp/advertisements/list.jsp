<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
</head>

<body>
<table width="900" border="1">
<form name="" action="" method="post">
  <tr>
    <td width="300">预览</td>
    <td><table width="500" border="1">
      <tr>
        <td width="30">广告位</td>
        <td width="168">&nbsp;</td>
      </tr>
      <tr>
        <td>广告名称</td>
        <td>
            <input type="text" name="textfield" />
        </td>
      </tr>
      <tr>
        <td>投放开始日期</td>
        <td><input type="text" name="textfield2" /></td>
      </tr>
      <tr>
        <td>投放结束日期</td>
        <td><input type="text" name="textfield3" /></td>
      </tr>
      <tr>
        <td>广告素材</td>
        <td><label>
          <input type="file" name="file" />
        </label></td>
      </tr>
      <tr>
        <td>素材形式，素材格式，素材宽高，大小限制</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="34">下方缩略图</td>
        <td>
          <input type="radio" name="radiobutton" value="radiobutton" />
        <label>将广告图缩略 </label><input type="radio" name="radiobutton" value="radiobutton" /><label>使用其他图片</label></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><input type="file" name="file2" /></td>
      </tr>
	  <tr>
        <td>链接地址</td>
        <td><input type="text" name="textfield22" /></td>
      </tr>
    </table></td>
  </tr>
  <input type="button" value="发布"/>
  <input type="button" value="取消"/>
</form> 
</table>
</body>
</html>
