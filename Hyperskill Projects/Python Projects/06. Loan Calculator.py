import argparse
import math

def calculate_diff_payments(principal, periods, interest):
    nominal_interest = interest / (12 * 100)
    total_payment = 0
    for m in range(1, periods + 1):
        diff_payment = math.ceil(principal / periods + nominal_interest * (principal - (principal * (m - 1)) / periods))
        total_payment += diff_payment
        print(f"Month {m}: payment is {diff_payment}")
    overpayment = total_payment - principal
    print("\nOverpayment =", overpayment)

def calculate_annuity_payment(principal, periods, interest):
    nominal_interest = interest / (12 * 100)
    annuity_payment = principal * (nominal_interest * math.pow(1 + nominal_interest, periods)) / (math.pow(1 + nominal_interest, periods) - 1)
    annuity_payment = math.ceil(annuity_payment)
    print(f"Your annuity payment = {annuity_payment}!")
    overpayment = annuity_payment * periods - principal
    print("Overpayment =", overpayment)

def calculate_periods(principal, payment, interest):
    if interest is None:
        print("Incorrect parameters")
        return

    nominal_interest = interest / (12 * 100)
    periods = math.log(payment / (payment - nominal_interest * principal), 1 + nominal_interest)
    periods = math.ceil(periods)
    years = periods // 12
    months = periods % 12
    if years > 0:
        print(f"It will take {years} years", end=" ")
        if months > 0:
            print(f"and {months} months", end=" ")
        print("to repay this loan!")
    else:
        print(f"It will take {months} months to repay this loan!")
    overpayment = payment * periods - principal
    print("Overpayment =", overpayment)


def calculate_principal(payment, periods, interest):
    nominal_interest = interest / (12 * 100)
    principal = payment / ((nominal_interest * math.pow(1 + nominal_interest, periods)) / (math.pow(1 + nominal_interest, periods) - 1))
    principal = math.ceil(principal)
    print(f"Your loan principal = {principal}!")
    overpayment = payment * periods - principal
    print("Overpayment =", overpayment)

def main():
    parser = argparse.ArgumentParser(description="Loan Calculator")
    parser.add_argument("--type", choices=["diff", "annuity"], required=True, help="Type of payment: 'diff' for differentiated, 'annuity' for annuity")
    parser.add_argument("--principal", type=float, help="Loan principal")
    parser.add_argument("--periods", type=int, help="Number of periods (months)")
    parser.add_argument("--interest", type=float, help="Loan interest rate (annual)")
    parser.add_argument("--payment", type=float, help="Monthly payment amount")
    args = parser.parse_args()

    if args.type == "diff":
        if not args.payment:
            calculate_diff_payments(args.principal, args.periods, args.interest)
        else:
            print("Incorrect parameters")
    elif args.type == "annuity":
        if not args.periods:
            calculate_periods(args.principal, args.payment, args.interest)
        elif not args.principal:
            calculate_principal(args.payment, args.periods, args.interest)
        elif not args.payment:
            calculate_annuity_payment(args.principal, args.periods, args.interest)
        else:
            print("Incorrect parameters")
    else:
        print("Incorrect type. Please choose 'diff' or 'annuity'.")

if __name__ == "__main__":
    main()
