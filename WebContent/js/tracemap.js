/**
 * 
 */
var path;
var svg;
var g;
var gunTraceData;
var gunTraceList = new Array(50);
var currentWidth;
var currentHeight;

/*******

loadStatesData - Calls web service to populate the states date

*/
function loadStatesData(year) {
	
	var webServiceUrl= "rest/GunTracingService/getGunTraces/" + String(year);

	d3.json(webServiceUrl, function(data) {
		gunTraceData = data;
	});
}

function clearMapColors() {

	if (g != null) {
        g.selectAll("path")
	        .classed("ExtraLow", function(cell) { return false; })
	        .classed("Low", function(cell) { return false; })
	        .classed("Medium", function(cell) { return false; })
	        .classed("High", function(cell)  { return false; })
	        .classed("ExtraHigh", function(cell) { return false; })
	        .classed("Extreme", function(cell) { return false; })
	        .classed("Selected", function(cell) { return false; });
	}
    document.getElementById("displayStateDetails").setAttribute("style","visibility: hidden;");
    document.getElementById("displayStateTable").setAttribute("style","visibility: hidden;");
    document.getElementById("stateName").innerHTML = "";
    document.getElementById("originState").innerHTML = "";
    document.getElementById("stateOfOrigin").innerHTML = "";
    document.getElementById("numGunsTraced").innerHTML = "";
    document.getElementById("pctAllTracedGuns").innerHTML = "";
    document.getElementById("gunsPerCapita").innerHTML = "";
}



/*******

    displayMap - Loads map shapes and gun data from JSON files and displays the map

    width, height - size (in pixels) of the displayed map.  Width is the important parameter due to the columnar layout of the display page

*/
function displayMap(width, height) {

	var x = width *  (900/width);
    var y = height * (800/height);
    var sizeString = "0 0 " + x + " " + y;
	
    path = d3.geo.path();
    
    svg = d3.select("div")
    	.append("div")
    	.append("svg")
    	//responsive SVG needs these 2 attributes and no width and height attr
    	.attr("preserveAspectRatio", "xMidYMid meet")
    	.attr("viewBox", sizeString)
    	.classed("svg-container", true) //container class to make it responsive
    	.classed("svg-content-responsive", true); 
   
    g = svg.append("g")
        .attr("id", "states");
  
    d3.json("json/statesMap.json", function(json) {
        g.selectAll("path")
            .data(json.features)
            .enter().append("path")
            .attr("d", path)
            .on("mouseover", findStatesReceivingGuns);
        
//        resizeMap(width, height);  // Enables resizing based on screen size/resolution
    	currentWidth = width;
    	currentHeight = height;
    });
}

/*******

 findStatesReceivingGuns(d)

 Call back function for the mouse over event on the states.  Once a state is selected, the trace data is retrieved and displayed on the map

 d - The state object representing the selected state.

 */
function findStatesReceivingGuns(d) {

    if (d != null) {
        g.selectAll("path")
            .classed("ExtraLow", function(cell) { return findMatchingStates(cell,d,1,25); })
            .classed("Low", function(cell) { return findMatchingStates(cell,d,25,50); })
            .classed("Medium", function(cell) { return findMatchingStates(cell,d,50,100); })
            .classed("High", function(cell) { return findMatchingStates(cell,d,100,500); })
            .classed("ExtraHigh", function(cell) { return findMatchingStates(cell,d,500,1000); })
            .classed("Extreme", function(cell) { return findMatchingStates(cell,d,1000,20000); })
            .classed("Selected", function(cell) { return cell === d; });
        createAccompanyingText(d);
    }
}



/*******

 findMatchingStates()

 Applied to all the states.  Selects appropriate coloring based on the number of guns that were traced in that state

 tgt - The state object representing the state where the guns were found.
 src - The state object representing the state where the guns originated
 bm - lower range of the band represented by this color
 tp - upper range of the band represented by this color

 */
function findMatchingStates(tgt, src, bm, tp) {

    cl = Number(src.id)-1;
    rw = Number(tgt.id)-1;
    nGuns = gunTraceData.states[cl].data[rw];
   return (nGuns >= bm) && (nGuns <=tp);

}

/*******

 resizeMap()

 Calculates the map size based on scale (which is determined from the current screen resolution).

 width - width set by developer
 height - height set by developer

 */
function resizeMap(width, height) {

	var scale = calculateScaling(width, height);
	x = width / 2;
    y = height / 2;
    var translateString = "translate(" + x + "," + y + ")scale(" + scale +")translate(" + -x + "," + -y + ")";
    g.selectAll("path").attr("transform", translateString);
}


function calculateScaling(width, height) {
	
	var vScale = height/800;
	var hScale = width/1000;
	
	if (vScale < hScale) {
		return vScale;
	}
	else {
		return hScale;
	}
	
}



/*******

 createAccompanyingText()

 Populated defined spans and divs in the HTML page based on the currently selected state

 d - The state object of the currently selected state
 */
function createAccompanyingText(d) {

    var iState;
    var nGuns = 0; // number of the guns traced to the current state
    var nTotGuns = 0;  // number of guns traced in all states
    var nStates = 0;  // number of states where guns were traced

    if (d != null) {
        iState = Number(d.id)-1;
        var stateName = gunTraceData.states[iState].name;

        for (var k in gunTraceData.states) {
            for (var j in gunTraceData.states[k].data) {
                nTotGuns += gunTraceData.states[k].data[j];
                if (k == iState) {
                    nGuns += gunTraceData.states[k].data[j];

                    if (gunTraceData.states[k].data[j] > 0) {
                        nStates++;
                    }
                }
            }
        }
        document.getElementById("displayStateDetails").setAttribute("style","visibility: visible;");
        document.getElementById("displayStateTable").setAttribute("style","visibility: visible;");
        document.getElementById("stateName").innerHTML = stateName;
        document.getElementById("originState").innerHTML = stateName;
//        document.getElementById("stateOfOrigin").innerHTML = stateName;
        document.getElementById("numGunsTraced").innerHTML = nmbFormatter(nGuns);
        document.getElementById("pctAllTracedGuns").innerHTML = pctFormatter(nGuns, nTotGuns);
        document.getElementById("gunsPerCapita").innerHTML = nmbFormatter(nGuns/gunTraceData.states[iState].population * 100000,2);
        displayGunTraceList(iState);
    }
}

/*******

 createGunTraceList() Populates the  10 recipients of guns table for the selected state.

 nState: numeric ID of the selected state.  TODO Make this similar to the other routines where  we retrieve the state object

 */
function displayGunTraceList(nState) {

	var tableSpace = document.getElementById("displayStateTable");
	var tableString = "<table><col width=50%><col width=25%><col width=25%><th>State</th><th class=numeric-cell>No. of Guns</span></th><th class=numeric-cell>% Guns Recovered in this State</th>";

    createGunTraceList(nState);

    for (var i=0;i<50;i++) {
    	if((gunTraceList[i][1]) > 0 ) {
	    	tableString = tableString  + "<tr><td class=text-cell>" + gunTraceList[i][0] + "</td>";
	    	tableString = tableString  + "<td class=numeric-cell>" + nmbFormatter(gunTraceList[i][1]) + "</td>";
	    	tableString = tableString  + "<td class=numeric-cell>" + pctFormatter(gunTraceList[i][1], gunTraceList[i][2]) + "</td></tr>";
    	}
    }
	tableString = tableString  + "</table>";
	tableSpace.innerHTML = tableString;
}

/*******

 createGunTraceList() Generates the underlying list to support generation of the top 10

 stateNum: numeric ID of the selected state.  TODO Make this similar to the other routines where  we retrieve the state object

 */
function createGunTraceList(stateNum) {

    clearGunTraceList();

    for (var i in gunTraceData.states[stateNum].data)   {
        for (var j = 0;j < 50;j++) {
            if (gunTraceData.states[stateNum].data[i] > gunTraceList[j][1]) {
                reshuffleGunTraceList(j);
                gunTraceList[j] = [gunTraceData.states[i].name, gunTraceData.states[stateNum].data[i],getTotalTracesForState(i)];
                j = 50;
            }
        }
    }
}


/*******

 reshuffleGunTraceList() Technique to support the sorting method.

 */
function reshuffleGunTraceList(startingPoint) {

    for (var i = (50-1); i > startingPoint;i--) {
        gunTraceList[i] = gunTraceList[i-1];
    }
}

function clearGunTraceList() {

    for (var i=0;i<50;i++) {
        gunTraceList[i] = [" ", 0, 0];
    }
}

/*******

 getTotalTracesForState() Counts traces from a specific state

 */
function getTotalTracesForState(stateNum) {

    var totTraces = 0;

    for (var i in gunTraceData.states[stateNum].data)  {
        totTraces += gunTraceData.states[stateNum].data[i];
    }
    return totTraces;
}

/*

Cheap & easy number Formatter.  Doesn't handle anything beyond a single comma.

*/
function nmbFormatter(num, decPlace) {

    if (num == 0) {
        return "0";
    }

    var outString = String(num);
    var integers, decimals;
    var dec = "";
    var decPtr = outString.indexOf(".");

    if (decPlace == null) {
        decPlace = -1;
    }

    if ((decPtr  > -1)) {
        integers = outString.substr(0,decPtr);

        if ((decPlace == -1)) {
            decimals = outString.substr(decPtr+1,outString.length-decPtr);
            dec = ".";
        }
        if ((decPlace == 0)) {
            decimals = "";
        }
        if ((decPlace > 0)) {
            decimals = outString.substr(decPtr+1,decPlace);
            dec = ".";
        }
    }
    else {
        integers = outString;
        decimals = "";
    }
    if (integers.length > 3) {
        integers = integers.substr(0,(integers.length - 3))+ "," + integers.substr((integers.length - 3), 3);
    }
    return (integers + dec + decimals);
}

/*

 Cheap & easy percent Formatter.  Doesn't handle anything beyond a single comma.

 */
function pctFormatter(num, den) {

    if ((num === 0) || (den === 0)) {
        return "0%";
    }

    var quotient = String(num / den);
    var ptr = quotient.indexOf(".");

    if (ptr > -1) {
        var integers = quotient.substr(ptr+1,2);

        if (integers.substr(0,1) == "0") {
            integers = integers.substr(1,1);
        }

        var decimals = String(quotient.substr(ptr+3,2));
        return integers + "." + decimals + "%"  ;
    }
    return quotient;
}