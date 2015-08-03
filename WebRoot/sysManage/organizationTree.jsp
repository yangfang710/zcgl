<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

 <%@ taglib uri="/WEB-INF/tld/organizationTree.tld" prefix="organizationTree" %>
<html>
    <head>

        <title></title>


<script type="text/javascript" src="../com_css/js/xtree.js"></script>
<script type="text/javascript" src="../com_css/js/xmlextras.js"></script>
<script type="text/javascript" src="../com_css/js/xloadtree.js"></script>
<link type="text/css" rel="stylesheet" href="../com_css/css/xtree.css" /> 
 
 <script type="text/javascript">


	function changeList(deptId)
    {
		if(deptId)
        {
			var org = deptId.split(":");
			var orgId = org[0];
			var orgName = org[1];
			if(orgId.indexOf(";;") != -1){
				orgId = orgId.substring(0,orgId.indexOf(";;"));
			}
			var listUrl = 'sysManage/organizationList.jsp?deptId='+orgId;
            window.parent.document.getElementById('content_frame').src = listUrl;
   		}
                	
     }
                
                </script> 
   
    </head>

    <body>  

 
 
          
    <organizationTree:organizationTree></organizationTree:organizationTree>

    </body>

    </html>

