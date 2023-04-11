package com.developjjong.singingstars.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty(message = "내용은 필수항목입니다.")
    @Size(max=200, message = ("200글자 이하여야 합니다."))
    private String content;
}
