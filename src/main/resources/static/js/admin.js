function filterData() {
    var filter = document.getElementById('order-sort').value;
    if(filter == "all"){
        location.reload();
    } else {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/admin/filter?sort=' + filter, true);
        xhr.onload = function() {
            if (this.status == 200) {
                var users = JSON.parse(this.responseText);
                console.log(users);
                var output = '';
                for(var i in users) {
                    output += '<tr>' +
                        '<td>' + users[i].idUser + '</td>' +
                        '<td>' + users[i].pseudo + '</td>' +
                        '<td>' + users[i].lastName + ' ' + users[i].firstName + '</td>';
                    if(filter != "deleted") {
                        output += '<td> <a href="/profil/' + users[i].idUser + '" class="btn btn-primary">' +
                            '    <button type="button" class="btn btn-primary">Voir profil</button>' +
                            '</a></td>' +
                            '<td> <a href="/admin/delete?user_id=' + users[i].idUser + '" class="btn btn-primary" onclick="return confirm(\'Êtes-vous sûr de vouloir supprimer cet utilisateur ?\')">' +
                            '    <button type="button" class="btn btn-danger">Suppression</button>' +
                            '</a></td>';
                        if(users[i].disabled) {
                            output += '<td> <a href="/admin/enable?user_id=' + users[i].idUser + '" class="btn btn-primary" onclick="return confirm(\'Êtes-vous sûr de vouloir réactiver cet utilisateur ?\')">' +
                                '    <button type="button" class="btn btn-primary">Réactivation</button>' +
                                '</a></td>';
                        } else {
                            output += '<td> <a href="/admin/disable?user_id=' + users[i].idUser + '" class="btn btn-primary" onclick="return confirm(\'Êtes-vous sûr de vouloir désactiver cet utilisateur ?\')">' +
                                '    <button type="button" class="btn danger">Désactivation</button>' +
                                '</a></td>';
                        }
                    }
                    output += '</tr>';
                }
                document.getElementById('user-table').getElementsByTagName('tbody')[0].innerHTML = output;
            }
        }
        xhr.send();
    }
}