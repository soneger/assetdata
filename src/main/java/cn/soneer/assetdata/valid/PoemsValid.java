package cn.soneer.assetdata.valid;

import lombok.Data;

import javax.annotation.RegEx;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * project：assetdata
 * class：PomesValid
 * describe：TODO
 * time：2021/3/20 18:22
 * author：soneer
 * version:1.0
 */

@Data
public class PoemsValid {
    @NotNull(message = "id 不能为空")
    @Pattern(regexp = "^[0-9]*$",message = "参数格式不正确")
    private String id;
    @NotNull(message = "typeId 不能为空")
    @Pattern(regexp = "^[0-9]*$",message = "参数格式不正确")
    private String typeId;
}
