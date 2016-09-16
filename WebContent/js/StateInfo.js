/**
 * 
 */

$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/RESTfulWebServiceExample/rest/StateInfo/getStatePopulation/AL/2003"
    }).then(function(data) {
       $('.stateAbbrev').append(data.stateAbbrev);
       $('.populationYear').append(data.populationYear);
       $('.population').append(data.population);
    });
});