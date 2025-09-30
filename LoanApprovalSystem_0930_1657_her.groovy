// 代码生成时间: 2025-09-30 16:57:51
package com.example.loanapprovalsystem

import grails.transaction.Transactional

// 定义贷款对象
class Loan {
    String applicantName
    BigDecimal loanAmount
    Date dateApplied
    boolean approved
    String approvalReason = ""

    static constraints = {
        applicantName blank: false
        loanAmount min: 0
        dateApplied nullable: true
        approved nullable: true
    }
}

// 贷款审批服务
@Transactional
class LoanApprovalService {
    def loanApprovalRepository // 用于查询和存储贷款申请的仓库

    // 申请贷款方法
    Loan applyForLoan(String applicantName, BigDecimal loanAmount) {
        def newLoan = new Loan(applicantName: applicantName, loanAmount: loanAmount)
        if (newLoan.validate()) {
            loanApprovalRepository.save(newLoan)
            return newLoan
        } else {
            throw new RuntimeException("Invalid loan application")
        }
    }

    // 审批贷款方法
    Loan approveLoan(Long loanId, boolean approve) {
        Loan loan = loanApprovalRepository.get(loanId)
        if (loan) {
            loan.approved = approve
            if (approve) {
                loan.approvalReason = "Loan approved based on good credit score"
            } else {
                loan.approvalReason = "Loan denied due to insufficient funds"
            }
            loanApprovalRepository.save(loan)
            return loan
        } else {
            throw new RuntimeException("Loan not found")
        }
    }
}

// REST控制器，处理HTTP请求
class LoanApprovalController {
    def loanApprovalService

    // 申请贷款的REST端点
    def apply() {
        def result = [success: false]
        try {
            def loan = loanApprovalService.applyForLoan(params.applicantName, params.loanAmount as BigDecimal)
            result.success = true
            result.loan = loan
            render result as JSON
        } catch (Exception e) {
            result.message = e.message
            render result as JSON
        }
    }

    // 审批贷款的REST端点
    def approve() {
        def result = [success: false]
        try {
            def loan = loanApprovalService.approveLoan(params.loanId as Long, params.approve as boolean)
            result.success = true
            result.loan = loan
            render result as JSON
        } catch (Exception e) {
            result.message = e.message
            render result as JSON
        }
    }
}
