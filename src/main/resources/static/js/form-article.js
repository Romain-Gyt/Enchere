// $(document).ready(function() {
//     function validateBid(input) {
//         var submitBtn = document.getElementById('submitBtn');
//         var minValue = parseInt(input.min);
//         var maxValue = parseInt(input.max);
//         var bidValue = parseInt(input.value);
//
//         console.log(minValue, maxValue, bidValue)
//
//         if (bidValue < minValue || bidValue > maxValue) {
//             submitBtn.disabled = true;
//         } else {
//             submitBtn.disabled = false;
//         }
//     }
//
//     window.onload = function() {
//         validateBid(document.querySelector('input[name=bidAmount]'));
//     };
// });