var paramDom = new ActiveXObject("Msxml2.DOMDocument.3.0");
paramDom.setProperty("SelectionLanguage", "XPath");
var paramRoot = paramDom.createElement("root");
paramDom.appendChild(paramRoot);

var tree;

function createTree() {
	tree = new WebFXTree('菜单权限', '', '', '');
    tree.setBehavior('classic');
    url = "#";
	actionurl = "groupAuthCreateTreeAction";
	doc = sendXml(actionurl);
	root = doc.selectSingleNode("root");
	if(root == null) {
		document.all("treeDiv").innerHTML = tree;
    	tree.expandAll();
		return;
	}
	var exMsg = root.selectSingleNode("exMsg");
  	if(exMsg != null) {
    	alert(exMsg.text);
    	return;
  	}
	
	firsts = root.selectNodes("first");
	//取得一级节点
	for(i=0; i<firsts.length; i++) {
		name = firsts[i].selectSingleNode("name").text;
		id = firsts[i].selectSingleNode("id").text;
		title = firsts[i].selectSingleNode("name").text;

		firstNode = new WebFXTreeItem(title, url, id, '1', '', '');
		tree.add(firstNode);
		
		seconds = firsts[i].selectNodes("second");
		for(ii=0; ii<seconds.length; ii++) {
			name = seconds[ii].selectSingleNode("name").text;
			id = seconds[ii].selectSingleNode("id").text;
			title = seconds[ii].selectSingleNode("name").text;
			secondNode = new WebFXTreeItem(title, url, id, '2', '', '');
			firstNode.add(secondNode);
			
			thirds = seconds[ii].selectNodes("third");
			for(iii=0; iii<thirds.length; iii++) {
				name = thirds[iii].selectSingleNode("name").text;
				id = thirds[iii].selectSingleNode("id").text;
				title = thirds[iii].selectSingleNode("name").text;
				thirdNode = new WebFXTreeItem(title, url, id, '3', '', '');
				secondNode.add(thirdNode);
			}
		}
	}
	
	document.all("treeDiv").innerHTML = tree;
    tree.expandAll();
}

function addPopedom() {
	
	if(tree.getSelected()) {
		node = tree.getSelected();
		if(node.nodeId == undefined) {
			node.nodeId = "";
			//alert("node no");
		}
		//alert("node.nodeId: "+node.nodeId);
		//alert("node selected");
		//alert("node.nodeId: "+node.nodeId);
		actionurl = "groupAuthGetPopedomGroupAction?elementId=" + node.nodeId;
		dom = sendXml(actionurl);
		root = dom.selectSingleNode("root");
		var exMsg = root.selectSingleNode("exMsg");
		if(exMsg != null) {
	    	alert(exMsg.text+"!!!!");
	    	return;
	  	}else{
	  		//alert("exNo");
	  	}
		result = root.selectNodes("result");
		//alert(result);
		addTable(result);
	}
}
/**
 * 删除权限
 */
function delPopedom() {
	nodeIdCheckObj = document.all("nodeId");
	if(nodeIdCheckObj == null) {
		return;
	}
	tableObj = document.all("popedomTable");
	if(nodeIdCheckObj.length > 0) {
		for(i=nodeIdCheckObj.length-1; i>=0; i--) {
			if(nodeIdCheckObj[i].checked) {
				for(ii=0; ii<paramRoot.selectNodes("nodeId").length; ii++) {
					if(paramRoot.selectNodes("nodeId")[ii].text == nodeIdCheckObj[i].value) {
						paramRoot.removeChild(paramRoot.selectNodes("nodeId")[ii]);
						break;
					}
				}
				tableObj.deleteRow(i+1);
			}
		}
	} else {
		if(nodeIdCheckObj.checked) {
			tableObj = document.all("popedomTable");
			tableObj.deleteRow(1);
			paramRoot.removeChild(paramRoot.selectSingleNode("nodeId"));
		}
	}
}

function addPopedomInit(menuId) {
	
		
		actionurl = "groupAuthModifyInitData?elementId=" +menuId;
		dom = sendXml(actionurl);
		root = dom.selectSingleNode("root");
		var exMsg = root.selectSingleNode("exMsg");
		if(exMsg != null) {
	    	alert(exMsg.text);
	    	return;
	  	}
		result = root.selectNodes("result");
		addTable(result);
	
}
function addPopedomInit2(menuId_Array) {
	//加载机制出现问题
	
	
	for(var i = 0 ;i<menuId_Array.length;i++){
	actionurl = "groupAuthModifyInitData?elementId=" +menuId_Array[i];
		
	
	dom = sendXml(actionurl);
	root = dom.selectSingleNode("root");
	var exMsg = root.selectSingleNode("exMsg");
	if(exMsg != null) {
    	alert(exMsg.text);
    	return;
  	}
	result = root.selectNodes("result");
	addTable(result);
	}
}


function addTable(result) {
	tableObj = document.all("popedomTable");
	for(i=0; i<result.length; i++) {
		if(isNodeExist(result[i].selectSingleNode("elementId").text)) {
			continue;
		}
		nodeIdTmpDoc = paramDom.createElement("nodeId");
		nodeIdTmpDoc.text = result[i].selectSingleNode("elementId").text;
		paramRoot.appendChild(nodeIdTmpDoc);
		
		var pRows = tableObj.rows;
  		var vRCount= pRows.length;
  		tableObj.insertRow(vRCount);
 		var row=pRows[vRCount];
  	
  		var col = row.insertCell();
  		col.align = "center";
  		col.innerHTML = vRCount;
  		
  		elementNameValue = "";
		if(result[i].selectSingleNode("elementGrade").text == "1") {
			elementGradeValue = "一级菜单";
			elementNameValue = "<font color='red'>" + result[i].selectSingleNode("elementName").text + "</font>";
		} if(result[i].selectSingleNode("elementGrade").text == "2") {
			elementGradeValue = "二级菜单";
			elementNameValue = "<font color='green'>&nbsp;&nbsp;&nbsp;" + result[i].selectSingleNode("elementName").text + "</font>";
		} if(result[i].selectSingleNode("elementGrade").text == "3") {
			elementGradeValue = "三级菜单";
			elementNameValue = "<font color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + result[i].selectSingleNode("elementName").text + "</font>";
		} 
		
		var col = row.insertCell();
  		col.align = "left";
  		col.innerHTML = elementNameValue;
  		
		var col = row.insertCell();
  		col.align = "left";
  		col.innerHTML = elementGradeValue;
  	
  		var col = row.insertCell();
  		col.align = "center";
  		col.innerHTML = "<input type='checkbox' name='nodeId' value='" + 
  			result[i].selectSingleNode("elementId").text + "' onclick='setChecked(this, \"" +
  			result[i].selectSingleNode("elementId").text + "\")'>";
  	}
}

function isNodeExist(nodeId) {
	nodeIdDoc = paramRoot.selectNodes("nodeId");
	if(nodeIdDoc.length == 0) {
		return false;
	}
	for(x=0; x<nodeIdDoc.length; x++) {
		if(nodeId == nodeIdDoc[x].text) {
			return true;
		}
	}
}

function setChecked(checkObj, nodeId) {
	oneNodes = tree.childNodes;
	checkNodes = [];
	index = 0;
	for(var i=0; i<oneNodes.length; i++) {
		//点击的节点为一级节点
		if(oneNodes[i].nodeId == nodeId) {
			checkNodes[index++] = oneNodes[i].nodeId;
			//取得所有二级子节点
			twoNodes = oneNodes[i].childNodes;
			for(var ii=0; ii<twoNodes.length; ii++) {
				checkNodes[index++] = twoNodes[ii].nodeId;
				threeNodes = twoNodes[ii].childNodes;
				for(var iii=0; iii<threeNodes.length; iii++) {
					checkNodes[index++] = threeNodes[iii].nodeId;
				}
			}
			
			break;
		}
	}
	if(checkNodes.length == 0) {
		//点击的节点可能是二级节点
		for(var i=0; i<oneNodes.length; i++) {
			twoNodes = oneNodes[i].childNodes;
			for(var ii=0; ii<twoNodes.length; ii++) {
				if(twoNodes[ii].nodeId == nodeId) {
					checkNodes[index++] = twoNodes[ii].nodeId;
					threeNodes = twoNodes[ii].childNodes;
					for(var iii=0; iii<threeNodes.length; iii++) {
						checkNodes[index++] = threeNodes[iii].nodeId;
					}
				}
			}
		}
	}
	
	checkObjs = document.all("nodeId");
	if(checkObjs.length > 0) {
		if(checkObj.checked) {
			for(var i=0; i<checkNodes.length; i++) {
				for(var ii=0; ii<checkObjs.length; ii++) {
					if(checkNodes[i] == checkObjs[ii].value) {
						checkObjs[ii].checked = true;
					}
				}
			}
		} else {
			for(var i=0; i<checkNodes.length; i++) {
				for(var ii=0; ii<checkObjs.length; ii++) {
					if(checkNodes[i] == checkObjs[ii].value) {
						checkObjs[ii].checked = false;
					}
				}
			}
		}
	}
	
}

function confirmAdd() {
	
	roleNameValue = document.all("roleName").value;
	var hidGroupId = document.all("hidGroupId").value;
	var txtDataAuthIDList = document.getElementById("txtDataAuthIDList").value;
	var txtProdAuthIDList = document.getElementById("txtProdAuthIDList").value;
	
	if(roleNameValue.trim() == "") {
		alert("请输入角色名称！");
		return;
	}
	if(hidGroupId.trim() == "") {
		alert("请选择父角色名称！");
		return;
	}
	roleDescValue = document.all("roleDesc").value;
	 //$.blockUI({message:'<h4><img src="../images/loading.gif" /> 系统正在提交中……</h4>' }); 
	 
	url = "groupAuthAddAction?roleName=" + roleNameValue + "&roleDesc=" + roleDescValue+ "&hidGroupId=" + hidGroupId+"&txtDataAuthIDList=" + txtDataAuthIDList+"&txtProdAuthIDList=" + txtProdAuthIDList;
	url = encodeURI(encodeURI(url));
	if(!confirm("确定已修改的权限？")) {
		return;
	}
	doc = sendXml(url, paramDom);
	root = doc.selectSingleNode("root");
	if(root != null) {
		var exMsg = root.selectSingleNode("exMsg");
	  	if(exMsg != null) {
	    	alert(exMsg.text);
	    	return;
	  	}
	}
	//$.unblockUI();
	if(confirm("增加成功，是否继续增加？")) {
		//清空表格
		document.all("roleName").value = "";
		document.all("roleDesc").value = "";
		var dataTable = new DataTable();
		dataTable.removeTableRow(document.all("popedomTable"));
		paramDom.removeChild(paramRoot);
		paramRoot = paramDom.createElement("root");
		paramDom.appendChild(paramRoot);
		setTab('tab1', 'oper', true);
		return;
	}
	document.all("addButton").disabled = true;
}


function confirmModify() {
	var groupId = document.getElementById("groupId").value;
	var roleName = document.getElementById("roleName").value;
	
	url = "groupAuthModifyAction?groupId=" + groupId;
	url = encodeURI(encodeURI(url));
	if(!confirm("确定已修改的权限？")) {
		return;
	}	
	doc = sendXml(url, paramDom);
	root = doc.selectSingleNode("root");
	alert("权限修改成功");
	return;
	//$.unblockUI();
	
	
}




function setTab(anan, divObj, isInit) {
	  var tabs = document.all("tab");
	  for(var s=0; s<tabs.length; s++) {
	    tabs[s].className = "";
	    if(tabs[s].lang == anan) {
	      tabs[s].className="current";
			if(divObj == "oper") {
				document.all("operAuthorityDiv").style.display = "block";
				document.all("dataAuthorityDiv").style.display = "none";
				
			    
			} else if(divObj == "data") {
				document.all("operAuthorityDiv").style.display = "none";
				document.all("dataAuthorityDiv").style.display = "block";
				
			} else if(divObj == "prod") {
				document.all("operAuthorityDiv").style.display = "none";
				document.all("dataAuthorityDiv").style.display = "none";
				
			} 
	    }
	  }
	}

//---------------------------数据权限---------------------------------
