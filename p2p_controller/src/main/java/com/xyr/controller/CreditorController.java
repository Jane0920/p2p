package com.xyr.controller;

import com.xyr.domain.CreditorModel;
import com.xyr.service.CreditorService;
import com.xyr.utils.ClaimsType;
import com.xyr.utils.Constant;
import com.xyr.utils.ConstantUtil;
import com.xyr.utils.RandomNumberUtil;
import com.xyr.utils.ResponseCode;
import com.xyr.utils.ServerResponse;
import com.xyr.utils.excelUtil.DataFormatUtilInterface;
import com.xyr.utils.excelUtil.ExcelDataFormatException;
import com.xyr.utils.excelUtil.MatchupData;
import com.xyr.utils.excelUtil.SimpleExcelUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xyr on 2017/9/13.
 */
@Controller
@RequestMapping("/creditor")
public class CreditorController {

    @Autowired
    private CreditorService creditorService;

    /**
     * 单笔债权录入
     *
     * @param creditor
     * @return
     */
    @RequestMapping("/addCreditor")
    @ResponseBody
    public ServerResponse addCreditor(CreditorModel creditor) {
        if (StringUtils.isEmpty(creditor.getContractNo())) { // 判断借款Id（合同编号）是否为空
            return ServerResponse.createByError(ResponseCode.CONTRACT_NO_IS_NULL.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtorsName())) { // 判断债务人是否为空
            return ServerResponse.createByError(ResponseCode.DEBTORS_NAME_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtorsId())) { // 判断身份证号是否为空
            return ServerResponse.createByError(ResponseCode.DEBTORS_ID_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getLoanPurpose())) { // 判断借款用途是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_PURPOSE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getLoanType())) { // 判断借款类型是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_TYPE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getLoanPeriod() + "")) { // 判断原始期限（月）是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_PERIOD_IS_EMPTY.getCode());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        if (StringUtils.isEmpty(sdf.format(creditor.getLoanStartDate()))) { // 判断原始借款开始日期是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_START_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(sdf.format(creditor.getLoanEndDate()))) { // 判断原始借款到期日期是否为空
            return ServerResponse.createByError(ResponseCode.LOAN_END_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getRepaymentStyle() + "")) { // 判断还款方式是否为空
            return ServerResponse.createByError(ResponseCode.REPAYMENT_STYLE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getRepaymenDate())) { // 判断还款日是否为空
            return ServerResponse.createByError(ResponseCode.REPAYMENT_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getRepaymenMoney() + "")) { // 判断还款金额（元）是否为空
            return ServerResponse.createByError(ResponseCode.REPAYMENT_MONEY_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtMoney() + "")) { // 判断债权金额（元）是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_MONEY_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtMonthRate() + "")) { // 判断债权年化利率（%）是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_MONTH_RATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtTransferredMoney() + "")) { // 判断债权转入金额是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_TRANSFERRED_MONEY_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(sdf.format(creditor.getDebtTransferredDate()))) { // 判断债权转入日期是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_TRANSFERRED_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getDebtTransferredPeriod() + "")) { // 判断债权转入期限（月）是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_TRANSFERRED_PERIOD_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(sdf.format(creditor.getDebtRansferOutDate()))) { // 判断债权转出日期是否为空
            return ServerResponse.createByError(ResponseCode.DEBT_RANSFER_OUT_DATE_IS_EMPTY.getCode());
        }
        if (StringUtils.isEmpty(creditor.getCreditor())) { // 判断债权人是否为空
            return ServerResponse.createByError(ResponseCode.CREDITOR_IS_EMPTY.getCode());
        }
        //录入债权信息
        creditor.setDebtNo("ZQNO" + RandomNumberUtil.randomNumber(new Date()));//债权id
        creditor.setBorrowerId(1);//贷款人id
        creditor.setDebtStatus(ClaimsType.UNCHECKDE); //债权状态
        creditor.setMatchedMoney(0.00);//匹配金额
        creditor.setMatchedStatus(ClaimsType.UNMATCH); //匹配状态
        creditor.setDebtType(Constant.NULL_SELECT_OUTACCOUNT);//标的类型
        creditor.setAvailablePeriod(creditor.getDebtTransferredPeriod());//可用期限
        creditor.setAvailableMoney(creditor.getDebtTransferredMoney());//可用金额
        creditorService.addCreditor(creditor);
        return ServerResponse.createBySuccess();
    }

    /**
     * 下载模板
     *
     * @param request
     * @param response
     */
    @RequestMapping("/download")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response) {
        //获取要下载的文件位置
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/excelTemplate/ClaimsBatchImportTemplate.xlsx");
        //设置两个响应头
        String mineType = request.getSession().getServletContext().getMimeType("ClaimsBatchImportTemplate.xlsx");
        response.setHeader("content-Type", mineType);
        response.setHeader("content-disposition", "attachment;fileName=" + new Date().toString() + ".xlsx");

        //获取输入流
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            OutputStream outputStream = response.getOutputStream();
            IOUtils.copy(fis, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 批量导入
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ServerResponse upload(MultipartFile[] file, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
        long date = new Date().getTime();

        //遍历要上传的文件
        for (MultipartFile mfile : file) {
            String destName = date + mfile.getOriginalFilename();
            FileInputStream fis = null;
            try {
                mfile.transferTo(new File(path, destName));
                fis = new FileInputStream(path + "/" + destName);
                SimpleExcelUtil<CreditorModel> seu = new SimpleExcelUtil<>();

                List<CreditorModel> creditorModels = seu.getDataFromExcle(fis, "", 1, new MatchupData<CreditorModel>() {
                    @Override
                    public <T> T macthData(List<Object> data, int indexOfRow, DataFormatUtilInterface formatUtil) {
                        CreditorModel creditor = new CreditorModel();
                        if (data.get(0) != null) {
                            creditor.setContractNo(data.get(0).toString()); // 债权合同编号
                        } else {
                            throw new ExcelDataFormatException("{" + 0 + "}");
                        }
                        creditor.setDebtorsName(data.get(1).toString().replaceAll("\\s{1,}", " "));// 债务人名称
                        // 身份证号
                        if (data.get(2) != null) {
                            String data2 = data.get(2).toString().replaceAll("\\s{1,}", " ");
                            String[] art = data2.split(" ");
                            for (int i = 0; i < art.length; i++) {
                                String str = art[i];
                                /*if (!RegValidationUtil.validateIdCard(str)) {
                                    throw new ExcelDataFormatException("{" + 2 + "}");
                                }*/
                            }
                            creditor.setDebtorsId(data2);// 债务人身份证编号
                        } else {
                            throw new ExcelDataFormatException("{" + 2 + "}");
                        }
                        creditor.setLoanPurpose(data.get(3).toString()); // 借款用途
                        creditor.setLoanType(data.get(4).toString());// 借款类型
                        creditor.setLoanPeriod(formatUtil.formatToInt(data.get(5), 5)); // 原始期限月
                        creditor.setLoanStartDate(formatUtil.format(data.get(6), 6));// 原始借款开始日期
                        creditor.setLoanEndDate(formatUtil.format(data.get(7), 7));// 原始贷款到期日期
                        // 还款方式
                        if (ConstantUtil.EqualInstallmentsOfPrincipalAndInterest.equals(data.get(8))) {// 等额本息
                            creditor.setRepaymentStyle(11601);
                        } else if (ConstantUtil.MonthlyInterestAndPrincipalMaturity.equals(data.get(8))) {// 按月付息到月还本
                            creditor.setRepaymentStyle(11602);
                        } else if (ConstantUtil.ExpirationTimeRepayment.equals(data.get(8))) {// 到期一次性还款
                            creditor.setRepaymentStyle(11603);
                        } else {
                            throw new ExcelDataFormatException("在单元格{" + 8 + "}类型不存在");
                        }
                        creditor.setRepaymenDate(data.get(9).toString());// 每月还款日
                        creditor.setRepaymenMoney(formatUtil.formatToDouble(data.get(10), 10));// 月还款金额
                        creditor.setDebtMoney(formatUtil.formatToDouble(data.get(11), 11));// 债权金额
                        creditor.setDebtMonthRate(formatUtil.formatToDouble(data.get(12), 12));// 债权月利率
                        creditor.setDebtTransferredMoney(formatUtil.formatToDouble(data.get(13), 13));// 债权转入金额
                        creditor.setDebtTransferredPeriod(formatUtil.formatToInt(data.get(14), 14));// 债权转入期限
                        creditor.setDebtRansferOutDate(formatUtil.format(data.get(15), 15));// 债权转出日期
                        creditor.setCreditor(data.get(16).toString());// 债权人

                        // 债权转入日期 原始开始日期+期限
                        Date startDate = formatUtil.format(data.get(6), 6); // 原始开始日期
                        int add = formatUtil.formatToInt(data.get(14), 14);// 期限
                        Calendar c = Calendar.getInstance();
                        c.setTime(startDate);
                        c.add(Calendar.MONTH, add);
                        creditor.setDebtTransferredDate(c.getTime());

                        Date da = new Date();
                        creditor.setDebtNo("ZQNO" + RandomNumberUtil.randomNumber(da));// 债权编号
                        creditor.setMatchedMoney(Double.valueOf("0"));// 已匹配金额
                        creditor.setDebtStatus(ClaimsType.UNCHECKDE); // 债权状态
                        creditor.setMatchedStatus(ClaimsType.UNMATCH);// 债权匹配状态
                        creditor.setBorrowerId(1); // 借款人id
                        creditor.setDebtType(Constant.NULL_SELECT_OUTACCOUNT); // 标的类型
                        creditor.setAvailablePeriod(creditor.getDebtTransferredPeriod());// 可用期限
                        creditor.setAvailableMoney(creditor.getDebtTransferredMoney());// 可用金额
                        return (T) creditor;
                    }
                });
                creditorService.addCreditor(creditorModels);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return ServerResponse.createBySuccess();
    }

}
