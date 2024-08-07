
    $(document).ready(function() {
    // Like button click event
    $('.heartBtn').on('click', function() {
        var diaryNo = $(this).data('no'); // Get diary ID from data-no attribute
        var type=$(this).data('type');
        var heartBtn = $(this);
        var heartIcon = heartBtn.find('i');
        var likeCountSpan = $('#like-count-' + diaryNo);

        var url = '/diaryBoard/toggleLike';

        if(type == 'diary'){
            url = '/diaryBoard/toggleLike';
        }else if(type == 'running'){
            url = '/runningBoard/toggleLike';
        }else if(type == 'qna'){
            url = '/qnaBoard/toggleLike';
        }else if(type == 'marathon'){
            url = '/marathonBoard/toggleLike';
        }

        $.ajax({
            type: 'POST',
            url: url,
            data: { no: diaryNo },
            success: function(response) {
                likeCountSpan.text(response.countLike); // Update the like count display

                if (response.liked) {
                    heartIcon.removeClass('fa-regular').addClass('fa-solid');
                } else {
                    heartIcon.removeClass('fa-solid').addClass('fa-regular');
                }
            },
            error: function() {
                alert('Error: Unable to toggle like.');
            }
        });
    });
});