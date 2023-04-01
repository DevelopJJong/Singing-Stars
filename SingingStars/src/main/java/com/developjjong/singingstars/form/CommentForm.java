package com.developjjong.singingstars.form;

import jakarta.validation.constraints.NotEmpty;

public class CommentForm {
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
