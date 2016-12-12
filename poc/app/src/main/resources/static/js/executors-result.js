function getAllValues() {
    var executorResults = new Array();
    executorResults.push(getValues('cloudVision'));
    executorResults.push(getValues('cognitiveServices'));
    executorResults.push(getValues('tess4j'));

    return [].concat.apply([],executorResults);
}

function getValues(executor) {
    var checkedValues = $('input[data-executor=' + executor + ']:checked');
    var values = new Array();
    $(checkedValues).each(function(index, element) {
        var value = $(element).next('label:first').html() === "✔";
        var name = $(element).attr('name');
        // console.info(name + ' : ' + value);
        values.push({ 'executor_name_result' : name, 'value' : value});
    });
    return values;

}

function updateExecutorValue() {
    var values = JSON.stringify(getAllValues());
    $('#executorResultId').val(values);
}

$( document ).ready(function() {
    var jsonObject = $('#executorResultId').val();
    var results = JSON.parse(jsonObject);

    $(results).each(function(index, element) {
        var inputs = $('input[name=' + element.executor_name_result + ']');
        if(element.value) {
            $(inputs[0]).prop('checked', true);
        } else {
            $(inputs[1]).prop('checked', true);
        }
    });
});