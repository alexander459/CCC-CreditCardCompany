var indexPage;
var registerPage;
var userPage;
var backBtn;
var rowBtnContainer;
var msg;

var user={};
var users=[];
var transactions=[];
window.addEventListener('load', ()=>{
    registerPage=document.getElementById('register-page');
    indexPage=document.getElementById('index-page');
    userPage=document.getElementById('user-page');

    msg=document.createElement('div');
    msg.setAttribute('class', 'msg-container');

    document.getElementById('create-btn').addEventListener('click', createAccountPage);
    document.getElementById('delete-btn').addEventListener('click', deleteAccountPage);
    document.getElementById('purchase-btn').addEventListener('click', purchasePage);
    document.getElementById('return-btn').addEventListener('click', returnMoneyPage);
    document.getElementById('payment-btn').addEventListener('click', payAmountPage);
    document.getElementById('questions-btn').addEventListener('click', questionsPage);
    document.getElementById('good-clients-btn').addEventListener('click', goodBadClientsPage);
    document.getElementById('good-clients-btn').setAttribute('id', 'good');
    document.getElementById('bad-client-btn').addEventListener('click', goodBadClientsPage);
    document.getElementById('bad-client-btn').setAttribute('id', 'bad');
    document.getElementById('dealer-month-btn').addEventListener('click', dealerOfTheMonthPage);

    document.getElementById('user-type-select').addEventListener('change', checkUser);
    document.getElementById('customer-type-select').addEventListener('change', checkUser);


    rowBtnContainer=document.createElement('div');
    rowBtnContainer.setAttribute('class', 'row-btn-container');
});




function createBackBtn(listener){
    var btn=document.createElement('button');
    btn.addEventListener('click', listener);
    btn.style.backgroundColor='var(--my_green)';
    btn.innerHTML='Back';
    return btn;
}

function showIndex(){
    userPage.style.display='none';
    registerPage.style.display='none';
    indexPage.style.display='block';
}

function createAccountPage(){
    backBtn=createBackBtn(showIndex);
    registerPage.style.display='block';
    indexPage.style.display='none';

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    registerPage.append(rowBtnContainer);
    checkUser();
}

function deleteAccountPage(){
    registerPage.style.display='none';
    indexPage.style.display='none';

    var input=document.createElement('input');
    var dltBtn=document.createElement('button');
    var backBtn;
    var form, tbl, tr, th, td;

    dltBtn.innerHTML='Delete';
    dltBtn.setAttribute('class', 'submit-btn');
    dltBtn.style.backgroundColor='red';
    backBtn=createBackBtn(showIndex);
    input.setAttribute('type', 'number');
    input.setAttribute('name', 'account_id');

    userPage.style.display='block';
    userPage.innerHTML="";

    form=document.createElement('form');
    form.setAttribute('onsubmit', 'deleteUser(); return false;');
    form.setAttribute('id', 'delete-form');

    tbl=document.createElement('table');
    tr=document.createElement('tr');
    th=document.createElement('th');
    td=document.createElement('td');

    th.innerHTML='Account ID';
    td.append(input);
    tr.append(th);
    tr.append(td);
    tbl.append(tr);

    tr=document.createElement('tr');
    th=document.createElement('th');
    th.setAttribute("colspan", "2");
    th.append(dltBtn);

    tr.append(th);
    tbl.append(tr);

    form.append(tbl);

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    //rowBtnContainer.append(dltBtn);
    userPage.append(form);
    userPage.append(rowBtnContainer);
}


function purchasePage(){
    var tbl, th, tr, td, form;
    var purchaseBtn, backBtn;
    var headers=['Customer account', 'Dealer account', 'Amount', 'Date', 'Employee ID'];
    var names=['customer_account', 'dealer_account', 'amount', 'date', 'employee_id'];
    var input;

    form=document.createElement('form');
    form.setAttribute('onsubmit', 'performPurchase(); return false;');
    form.setAttribute('id', 'purchase-form');

    purchaseBtn=document.createElement('button');
    purchaseBtn.setAttribute('class', 'submit-btn');
    purchaseBtn.style.fontSize='15px';
    purchaseBtn.innerHTML='Purchase';

    backBtn=createBackBtn(showIndex);
    tbl=document.createElement('table');
    for(var i=0; i<headers.length; i++){
        tr=document.createElement('tr');
        th=document.createElement('th');
        td=document.createElement('td');

        th.innerHTML=headers[i];

        input=document.createElement('input');
        input.setAttribute('name', names[i]);

        switch (i){
            case 2:
                input.setAttribute('type', 'number');
                input.setAttribute('step', '0.01');
                input.setAttribute('required', '');
                td.append(input);
                break;
            case 3:
                input.setAttribute('type', 'date');
                input.setAttribute('required', '');
                td.append(input);
                break;
            case 4:
                input.setAttribute('type', 'number');
                td.append(input);
                break;
            default:
                input.setAttribute('type', 'number');
                input.setAttribute('required', '');
                td.append(input);
                break;
        }

        tr.append(th)
        tr.append(td);
        tbl.append(tr);
    }


    tr=document.createElement('tr');
    th=document.createElement('th');
    th.setAttribute("colspan", "2");
    th.append(purchaseBtn);
    tr.append(th);
    tbl.append(tr);

    form.append(tbl);

    rowBtnContainer.innerHTML='';
    rowBtnContainer.append(backBtn);

    userPage.style.display='block';
    indexPage.style.display='none';
    userPage.innerHTML='';
    userPage.append(form);
    userPage.append(rowBtnContainer);
}

function returnMoneyPage(){
    var tbl, th, tr, td, form;
    var returnBtn, backBtn;
    var headers=['Customer account', 'Dealer account', 'Amount', 'Date', 'Employee ID'];
    var names=['customer_account', 'dealer_account', 'amount', 'date', 'employee_id'];
    var input;

    form=document.createElement('form');
    form.setAttribute('onsubmit', 'performReturn(); return false;');
    form.setAttribute('id', 'return-form');

    returnBtn=document.createElement('button');
    returnBtn.setAttribute('class', 'submit-btn');
    returnBtn.style.fontSize='15px';
    returnBtn.innerHTML='Return';

    backBtn=createBackBtn(showIndex);
    tbl=document.createElement('table');
    for(var i=0; i<headers.length; i++){
        tr=document.createElement('tr');
        th=document.createElement('th');
        td=document.createElement('td');

        th.innerHTML=headers[i];

        input=document.createElement('input');
        input.setAttribute('name', names[i]);
        input.setAttribute('type', 'number');
        if(i===2){
            input.setAttribute('step', '0.01');
        }else if(i===3){
            input.setAttribute('type', 'date');
        }
        if(i!==4){
            input.setAttribute('required', '');
        }
        td.append(input);

        tr.append(th)
        tr.append(td);
        tbl.append(tr);
    }

    tr=document.createElement('tr');
    th=document.createElement('th');
    th.setAttribute("colspan", "2");
    th.append(returnBtn);
    tr.append(th);
    tbl.append(tr);

    form.append(tbl);

    rowBtnContainer.innerHTML='';
    rowBtnContainer.append(backBtn);

    userPage.style.display='block';
    indexPage.style.display='none';
    userPage.innerHTML='';
    userPage.append(form);
    userPage.append(rowBtnContainer);
}

function payAmountPage(){
    var backBtn;
    var tbl, tr, th, td, payBtn, form, input;

    backBtn=createBackBtn(showIndex);
    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);

    payBtn=document.createElement('button');
    payBtn.innerHTML='Pay';
    payBtn.setAttribute('class', 'submit-btn');

    form=document.createElement('form');
    form.setAttribute('id', 'pay-form');
    form.setAttribute('onsubmit', 'payDept(); return false;');

    tbl=document.createElement('table');
    tr=document.createElement('tr');
    th=document.createElement('th');
    td=document.createElement('td');

    input=document.createElement('input');
    input.setAttribute('name', 'account_id');
    input.setAttribute('required', '');
    input.setAttribute('type', 'number');

    th.innerHTML='Account ID';
    td.append(input);

    tr.append(th);
    tr.append(td);

    tbl.append(tr);

    tr=document.createElement('tr');
    th=document.createElement('th');
    th.setAttribute('colspan', '2');
    th.append(payBtn);

    tr.append(th);
    tbl.append(tr);

    form.append(tbl);

    userPage.style.display='block';
    indexPage.style.display='none';

    userPage.innerHTML='';
    userPage.append(form);

    userPage.append(rowBtnContainer);

}

function getUserPage(){
    registerPage.style.display='none';
    indexPage.style.display='none';

    var input=document.createElement('input');
    var getBtn=document.createElement('button');
    var backBtn;
    var form, tbl, tr, th, td;

    getBtn.innerHTML='Get';
    getBtn.setAttribute('class', 'submit-btn');
    getBtn.style.backgroundColor='var(--my_yellow)';
    backBtn=createBackBtn(questionsPage);
    input.setAttribute('type', 'number');
    input.setAttribute('name', 'account_id');

    userPage.style.display='block';
    userPage.innerHTML="";

    form=document.createElement('form');
    form.setAttribute('onsubmit', 'getUser(); return false;');
    form.setAttribute('id', 'get-form');

    tbl=document.createElement('table');
    tr=document.createElement('tr');
    th=document.createElement('th');
    td=document.createElement('td');

    th.innerHTML='Account ID';
    td.append(input);
    tr.append(th);
    tr.append(td);
    tbl.append(tr);

    tr=document.createElement('tr');
    th=document.createElement('th');
    th.setAttribute("colspan", "2");
    th.append(getBtn);

    tr.append(th);
    tbl.append(tr);

    form.append(tbl);

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);

    userPage.append(form);
    userPage.append(rowBtnContainer);
}

function questionsPage(){
    var div=document.createElement('div');
    var transactionsInfo=document.createElement('button');
    var userInfoBtn=document.createElement('button');
    var backBtn;
    backBtn=createBackBtn(showIndex);

    transactionsInfo.innerHTML='Transactions Info';
    transactionsInfo.addEventListener('click', transactionsPage);
    userInfoBtn.innerHTML='User Info';
    userInfoBtn.addEventListener('click', getUserPage);

    div.setAttribute('class', 'button-container');
    div.append(transactionsInfo);
    div.append(userInfoBtn);
    rowBtnContainer.innerHTML='';
    rowBtnContainer.append(backBtn);

    indexPage.style.display='none';
    userPage.style.display='block';
    userPage.innerHTML='';
    userPage.append(div);
    userPage.append(rowBtnContainer);
}

function transactionsPage(){
    var backBtn;
    var form, tbl, th, tr, td;
    var headers=['Account ID', 'Start Date', 'End Date', 'Employee ID'];
    var names=['account_id', 'start_date', 'end_date', 'employee_id'];
    var input, showBtn;

    showBtn=document.createElement('button');
    showBtn.setAttribute('class', 'submit-btn');
    showBtn.innerHTML='Show';

    backBtn=createBackBtn(questionsPage);

    form=document.createElement('form');
    form.setAttribute('id', 'transactions-form');
    form.setAttribute('onsubmit', 'getTransactions();return false;');
    tbl=document.createElement('table');

    for(var i=0; i<headers.length; i++){
        tr=document.createElement('tr');
        td=document.createElement('td');
        th=document.createElement('th');
        th.innerHTML=headers[i];
        input=document.createElement('input');
        input.setAttribute('name', names[i]);
        if(i===1 || i===2)
            input.setAttribute('type', 'date');
        else
            input.setAttribute('type', 'number');

        if(i!==3)
            input.setAttribute('required', '');

        td.append(input);
        tr.append(th);
        tr.append(td);
        tbl.append(tr);
    }

    tr=document.createElement('tr');
    th=document.createElement('th');
    th.setAttribute('colspan', '2');
    th.append(showBtn);
    tr.append(th);
    tbl.append(tr);

    form.append(tbl);

    rowBtnContainer.innerHTML='';
    rowBtnContainer.append(backBtn);
    userPage.innerHTML='';
    userPage.append(form);
    userPage.append(rowBtnContainer);
}

function displayTransactions(){
    var headers=['Employee ID', 'Customer Account', 'Dealer Account', 'Date', 'Amount', 'Type'];
    var keys=['employee_id', 'customer_account', 'dealer_account', 'date', 'amount', 'transaction_type'];
    var tbl, th, tr, td, tHead, tBody, div;
    var backBtn;


    userPage.style.display='block';
    indexPage.style.display='none';

    backBtn=createBackBtn(transactionsPage);

    //the table
    tbl=document.createElement('table');
    tbl.setAttribute('border', '1px');

    //the table head
    tHead=document.createElement('thead');

    //the table body
    tBody=document.createElement('tbody');

    //the table container
    div=document.createElement('div');
    div.setAttribute("class", 'scroll-container');
    div.style.width='50%';

    //create the headers
    //create the row for the headers
    tr=document.createElement('tr');
    for(var i=0; i<headers.length; i++){
        th=document.createElement('th');
        th.innerHTML=headers[i];
        tr.append(th);
    }
    tHead.append(tr);

    //create the body
    for(i=0; i<transactions.length; i++){
        tr=document.createElement('tr');
        for(var j=0; j<keys.length; j++){
            td=document.createElement('td');
            td.innerHTML=(transactions[i][keys[j]]);
            tr.append(td);
        }
        tHead.append(tr);
    }

    tbl.append(tHead);
    tbl.append(tBody);
    div.append(tbl);

    userPage.innerHTML='';
    userPage.append(div);

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    userPage.append(rowBtnContainer);
}

function goodBadClientsPage(){
    var headers=['Account ID', 'Username'];
    var keys=['account_id', 'username']
    var tbl, th, tr, td, tHead, tBody, div;
    var backBtn;

    if(event.srcElement.id=='good')
        getGoodClients();
    else
        getBadClients();

    if(users===null){
        return;
    }
    userPage.style.display='block';
    indexPage.style.display='none';

    backBtn=createBackBtn(showIndex);

    //the table
    tbl=document.createElement('table');
    tbl.setAttribute('border', '1px');

    //the table head
    tHead=document.createElement('thead');

    //the table body
    tBody=document.createElement('tbody');

    //the table container
    div=document.createElement('div');
    div.setAttribute("class", 'scroll-container');

    //create the headers
    //create the row for the headers
    tr=document.createElement('tr');
    for(var i=0; i<headers.length; i++){
        th=document.createElement('th');
        th.innerHTML=headers[i];
        tr.append(th);
    }
    tHead.append(tr);

    //create the body
    for(i=0; i<users.length; i++){
        tr=document.createElement('tr');
        for(var j=0; j<keys.length; j++){
            td=document.createElement('td');
            td.innerHTML=(users[i][keys[j]]);
            tr.append(td);
        }
        tHead.append(tr);
    }

    tbl.append(tHead);
    tbl.append(tBody);
    div.append(tbl);

    userPage.innerHTML='';
    userPage.append(div);

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    userPage.append(rowBtnContainer);
}



function dealerOfTheMonthPage(){
    userPage.style.display='block';
    indexPage.style.display='none';
    userPage.innerHTML="";

    var backBtn=createBackBtn(showIndex);
    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    userPage.append(rowBtnContainer);

    getDealerOfMonth();
}


function displayUserData(data){
    var backBtn=createBackBtn(showIndex);
    var keys=[];
    var values=[];
    var tbl, th, tr, td;
    var form;


    registerPage.style.display='none';
    indexPage.style.display='none';
    userPage.style.display='block';
    userPage.innerHTML="";

    keys=Object.keys(data);
    values=Object.values(data);

    form=document.createElement('form');
    tbl=document.createElement('table');

    for(var i=0; i<keys.length; i++){
        tr=document.createElement('tr');
        th=document.createElement('th');
        td=document.createElement('td');
        th.innerHTML=keys[i];
        td.innerHTML=values[i];
        tr.append(th);
        tr.append(td);
        tbl.append(tr);
    }



    form.append(tbl);
    userPage.append(form);
    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn)
    userPage.append(rowBtnContainer)
}

//takes a form and returns the params query string
function formToParams(form){
    let formData=new FormData(form);
    var params="";
    for (let [name, value] of formData) {
        params+=name + "=" + value + "&";
    }
    params=params.substring(0, params.length - 1);
    return params;
}

/*hides the not necessary inputs*/
function checkUser(){
    var userType=document.getElementById('user-type-select').value;
    switch (userType){
        case 'dealer':
            document.getElementById('supply-input').disabled=false;
            document.getElementById('username_input').disabled=false;

            document.getElementById('exp-date-input').disabled=true;
            document.getElementById('customer-type-select').disabled=true;
            document.getElementById('credit-limit-input').disabled=true;
            document.getElementById('accountid-input').disabled=true;
            document.getElementById('employee-name-input').disabled=true;
            break;
        case 'customer':
            document.getElementById('exp-date-input').disabled=false;
            document.getElementById('customer-type-select').disabled=false;
            document.getElementById('credit-limit-input').disabled=false;
            document.getElementById('username_input').disabled=false;

            document.getElementById('employee-name-input').disabled=true;
            document.getElementById('accountid-input').disabled=true;
            document.getElementById('supply-input').disabled=true;
            break;
        case 'employee':
            document.getElementById('accountid-input').disabled=false;
            document.getElementById('employee-name-input').disabled=false;

            document.getElementById('username_input').disabled=true;
            document.getElementById('customer-type-select').disabled=true;
            document.getElementById('supply-input').disabled=true;
            document.getElementById('credit-limit-input').disabled=true;
            document.getElementById('exp-date-input').disabled=true;
            break;
    }
}
