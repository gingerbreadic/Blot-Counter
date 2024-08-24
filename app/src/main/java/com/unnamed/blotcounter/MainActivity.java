package com.unnamed.blotcounter;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;


public class MainActivity extends AppCompatActivity {

    TextView textView1, textView4;
    TextView roundNumber, roundScore, terz1, terz2, we, you, quansh;
    int roundTerz1, roundTerz2, roundNumberInt;
    String result;
    TextView roundTextView;
    int totalTeam1, totalTeam2;
    boolean quanshed, sharped;
    boolean said;
    boolean scoreSet;
    private LinearLayout scoresDisplayContainer;
    private boolean updateTerz1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        scoresDisplayContainer = findViewById(R.id.scoresDisplayContainer);
        textView1 = findViewById(R.id.textView1);
        textView4 = findViewById(R.id.textView4);
        roundNumber = findViewById(R.id.roundNumber);
        roundScore = findViewById(R.id.roundScore);
        quansh = findViewById(R.id.quansh);
        textView1.setEnabled(false);
        roundScore.setEnabled(false);
        textView4.setEnabled(false);
        roundTerz1 = 0;
        roundTerz2 = 0;
        totalTeam2 = 0;
        totalTeam1 = 0;
        quanshed = false;
        sharped = false;
        scoreSet = false;
        said = false;
        quansh.setEnabled(said);


        textView1.setOnClickListener(v -> {
            updateTerz1 = true; // Update terz1
            showAlertDialog();
            if (roundTerz1 <= 0) {
                roundTerz1 = 0;
            }
        });

        textView4.setOnClickListener(v -> {
            updateTerz1 = false; // Update terz2
            showAlertDialog();

        });

        roundNumber.setOnClickListener(v -> {
            setRoundNumber();
        });

        roundScore.setOnClickListener(v -> {
            if (roundScore.isEnabled()){
                setRoundScore(result);
            }
        });

        quansh.setOnClickListener(v -> {
            if (!quanshed) {
                setQuansh(quansh);
            } else if (!sharped) {
                setQuansh(quansh);
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private void showAlertDialog() {
        // Create an EditText for the alert dialog
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED); // Allow negative numbers
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // Center alignment
        editText.setTextColor(R.color.black);

        // Create the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(editText);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String enteredText = editText.getText().toString().trim();

            // Validate if enteredText is a valid number
            if (isValidNumber(enteredText)) {
                // Calculate sum only if input is a valid number
                int sum = calculateSum(enteredText);

                // Update UI based on sum
                if (updateTerz1) {
                    roundTerz1 += sum;
                    if (roundTerz1 < 0) {
                        roundTerz1 = 0;
                    }
                    if (terz1 != null) {
                        terz1.setText(String.valueOf(roundTerz1));
                    }
                } else {
                    roundTerz2 += sum;
                    if (roundTerz2 < 0) {
                        roundTerz2 = 0;
                    }
                    if (terz2 != null) {
                        terz2.setText(String.valueOf(roundTerz2));
                    }
                }
            } else {
                // Handle invalid input (optional: show error message)
                Toast.makeText(getApplicationContext(), "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        dialog.show();
    }

    private boolean isValidNumber(String input) {
        // Check if input is a valid number (including negative numbers)
        return input.matches("-?\\d+");
    }

    private int calculateSum(String input) {
        int sum = 0;

        // Replace all non-digit characters except '-' with spaces
        input = input.replaceAll("[^\\d-]", " ");

        // Split the cleaned text by spaces to get individual number strings
        String[] numberStrings = input.trim().split("\\s+");

        // Calculate sum of valid numbers
        for (String numberStr : numberStrings) {
            if (!TextUtils.isEmpty(numberStr)) {
                int number = Integer.parseInt(numberStr.trim());
                sum += number;
            }
        }

        return sum;
    }


    @SuppressLint("ResourceAsColor")
    public void setRoundNumber() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create a ScrollView to hold the layout
        ScrollView scrollView = new ScrollView(this);

        // Create a linear layout to hold the buttons and edit text
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER); // Center the contents vertically
        scrollView.addView(layout);

        // Create buttons for the first group
        LinearLayout.LayoutParams buttonParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Adjust width as needed
                LinearLayout.LayoutParams.WRAP_CONTENT // Adjust height as needed
        );
        buttonParams1.setMargins(0, 20, 0, 20); // Set margins (left, top, right, bottom)

        LinearLayout buttonGroup1 = new LinearLayout(this);
        buttonGroup1.setOrientation(LinearLayout.HORIZONTAL);
        buttonGroup1.setGravity(Gravity.CENTER);

        Button buttonPlus = new Button(this);
        buttonPlus.setText("+");
        buttonPlus.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24); // Set text size to 24dp
        buttonPlus.setTextColor(BLACK);
        buttonPlus.setLayoutParams(buttonParams1);

        Button buttonMinus = new Button(this);
        buttonMinus.setText("-");
        buttonMinus.setTextColor(BLACK);
        buttonMinus.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24); // Set text size to 24dp
        buttonMinus.setLayoutParams(buttonParams1);

        buttonGroup1.addView(buttonPlus);
        buttonGroup1.addView(buttonMinus);

        // Set default checked button
        buttonPlus.setSelected(true);
        buttonPlus.setBackgroundResource(R.drawable.backgroundstatefirst);

        // Create buttons for the second group
        LinearLayout buttonGroup2 = new LinearLayout(this);
        buttonGroup2.setOrientation(LinearLayout.HORIZONTAL);
        buttonGroup2.setGravity(Gravity.CENTER);

        // Define button properties for all options
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        buttonParams.setMargins(0, 20, 0, 20);

        Button option1 = new Button(this);
        option1.setText("♥️");
        option1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24); // Set text size to 24dp
        option1.setLayoutParams(buttonParams);
        Button option2 = new Button(this);
        option2.setText("♦️");
        option2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24); // Set text size to 24dp
        option2.setLayoutParams(buttonParams);
        Button option3 = new Button(this);
        option3.setText("♣️");
        option3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24); // Set text size to 24dp
        option3.setLayoutParams(buttonParams);
        Button option4 = new Button(this);
        option4.setText("♠️");
        option4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24); // Set text size to 24dp
        option4.setLayoutParams(buttonParams);
        Button option5 = new Button(this);
        option5.setText("X");
        option5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24); // Set text size to 24dp
        option5.setLayoutParams(buttonParams);

        buttonGroup2.addView(option1);
        buttonGroup2.addView(option2);
        buttonGroup2.addView(option3);
        buttonGroup2.addView(option4);
        buttonGroup2.addView(option5);

        option1.setSelected(true); // Set first option checked by default
        option1.setBackgroundResource(R.drawable.backgroundstatefirst);
        buttonMinus.setBackgroundResource(R.drawable.backgroundstatesecond);
        option2.setBackgroundResource(R.drawable.backgroundstatesecond);
        option3.setBackgroundResource(R.drawable.backgroundstatesecond);
        option4.setBackgroundResource(R.drawable.backgroundstatesecond);
        option5.setBackgroundResource(R.drawable.backgroundstatesecond);

        // Create an EditText
        TextView textView = new TextView(this);
        textView.setText("Խոսացած");
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24); // Set text size to 24dp
        textView.setTextColor(BLACK);

        EditText editText = new EditText(this);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHintTextColor(Color.GRAY);
        editText.setTextColor(Color.BLACK);

        // Add the button groups and edit text to the layout
        layout.addView(buttonGroup1);
        layout.addView(buttonGroup2);
        layout.addView(textView);
        layout.addView(editText);

        builder.setView(scrollView);

        // Set up the buttons
        builder.setPositiveButton("OK", null);

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            roundNumber.setEnabled(true);
            textView1.setEnabled(false);
            textView4.setEnabled(false);
            roundScore.setEnabled(false);
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
            Button selectedButton1 = buttonPlus.isSelected() ? buttonPlus : buttonMinus;
            Button selectedButton2 = option1.isSelected() ? option1 :
                    option2.isSelected() ? option2 :
                            option3.isSelected() ? option3 :
                                    option4.isSelected() ? option4 :
                                            option5;

            String selectedOption1 = selectedButton1.getText().toString();
            String selectedOption2 = selectedButton2.getText().toString();
            String inputText = editText.getText().toString();
            if (!inputText.isEmpty()){
                if (Integer.parseInt(inputText) < 8) {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                } else {
                    roundNumberInt = Integer.parseInt(inputText);

                    result = selectedOption1 + " " + selectedOption2 + " " + inputText;
                    roundNumber.setEnabled(false);

                    quansh.setEnabled(true);
                    roundScore.setEnabled(true);
                    roundScore.setClickable(true);
                    roundScore.setFocusable(true);
                    roundTerz1 = 0;
                    roundTerz2 = 0;
                    textView1.setEnabled(true);
                    textView4.setEnabled(true);
                    System.out.println(roundScore.isEnabled());

                    addScorePair(result);
                    dialog.dismiss();
                }
            }else{
                System.out.println("You need to type number.");
            }
        }));

        // Add click listeners to toggle the button states
        buttonPlus.setOnClickListener(view -> {
            buttonPlus.setSelected(true);
            buttonPlus.setBackgroundResource(R.drawable.backgroundstatefirst);
            buttonMinus.setSelected(false);
            buttonMinus.setBackgroundResource(R.drawable.backgroundstatesecond);
        });

        buttonMinus.setOnClickListener(view -> {
            buttonPlus.setSelected(false);
            buttonPlus.setBackgroundResource(R.drawable.backgroundstatesecond);
            buttonMinus.setSelected(true);
            buttonMinus.setBackgroundResource(R.drawable.backgroundstatefirst);
        });

        option1.setOnClickListener(view -> {
            option1.setSelected(true);
            option1.setBackgroundResource(R.drawable.backgroundstatefirst);
            option2.setSelected(false);
            option2.setBackgroundResource(R.drawable.backgroundstatesecond);
            option3.setSelected(false);
            option3.setBackgroundResource(R.drawable.backgroundstatesecond);
            option4.setSelected(false);
            option4.setBackgroundResource(R.drawable.backgroundstatesecond);
            option5.setSelected(false);
            option5.setBackgroundResource(R.drawable.backgroundstatesecond);
        });

        option2.setOnClickListener(view -> {
            option1.setSelected(false);
            option1.setBackgroundResource(R.drawable.backgroundstatesecond);
            option2.setSelected(true);
            option2.setBackgroundResource(R.drawable.backgroundstatefirst);
            option3.setSelected(false);
            option3.setBackgroundResource(R.drawable.backgroundstatesecond);
            option4.setSelected(false);
            option4.setBackgroundResource(R.drawable.backgroundstatesecond);
            option5.setSelected(false);
            option5.setBackgroundResource(R.drawable.backgroundstatesecond);
        });

        option3.setOnClickListener(view -> {
            option1.setSelected(false);
            option1.setBackgroundResource(R.drawable.backgroundstatesecond);
            option2.setSelected(false);
            option2.setBackgroundResource(R.drawable.backgroundstatesecond);
            option3.setSelected(true);
            option3.setBackgroundResource(R.drawable.backgroundstatefirst);
            option4.setSelected(false);
            option4.setBackgroundResource(R.drawable.backgroundstatesecond);
            option5.setSelected(false);
            option5.setBackgroundResource(R.drawable.backgroundstatesecond);
        });

        option4.setOnClickListener(view -> {
            option1.setSelected(false);
            option1.setBackgroundResource(R.drawable.backgroundstatesecond);
            option2.setSelected(false);
            option2.setBackgroundResource(R.drawable.backgroundstatesecond);
            option3.setSelected(false);
            option3.setBackgroundResource(R.drawable.backgroundstatesecond);
            option4.setSelected(true);
            option4.setBackgroundResource(R.drawable.backgroundstatefirst);
            option5.setSelected(false);
            option5.setBackgroundResource(R.drawable.backgroundstatesecond);
        });

        option5.setOnClickListener(view -> {
            option1.setSelected(false);
            option1.setBackgroundResource(R.drawable.backgroundstatesecond);
            option2.setSelected(false);
            option2.setBackgroundResource(R.drawable.backgroundstatesecond);
            option3.setSelected(false);
            option3.setBackgroundResource(R.drawable.backgroundstatesecond);
            option4.setSelected(false);
            option4.setBackgroundResource(R.drawable.backgroundstatesecond);
            option5.setSelected(true);
            option5.setBackgroundResource(R.drawable.backgroundstatefirst);
        });
        said = true;
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        dialog.show();
    }


    private void addScorePair(String roundNumber) {
        LinearLayout scorePairLayout = new LinearLayout(this);
        scorePairLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 16, 0, 16);
        scorePairLayout.setLayoutParams(layoutParams);

        // Create TextViews for displaying Team 1, Round Number, Team 2 scores, and who said the number
        terz1 = new TextView(this);
        terz1.setText(String.valueOf(0));
        terz1.setTextSize(18); // Smaller text size for terz1
        terz1.setTextColor(BLACK);
        terz1.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f // Less weight to make it less prominent
        ));
        terz1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        we = new TextView(this);
        we.setText(String.valueOf(0));
        we.setTextSize(24);
        we.setTextColor(BLACK);
        we.setTypeface(null, Typeface.BOLD);
        we.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        we.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        you = new TextView(this);
        you.setText(String.valueOf(0));
        you.setTextSize(24);
        you.setTextColor(BLACK);
        you.setTypeface(null, Typeface.BOLD);
        you.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        you.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        terz2 = new TextView(this);
        terz2.setText(String.valueOf(0));
        terz2.setTextSize(18);
        terz2.setTextColor(BLACK);
        terz2.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f // Less weight to make it less prominent
        ));
        terz2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        roundTextView = new TextView(this);
        roundTextView.setText(roundNumber);
        roundTextView.setTextSize(18);
        roundTextView.setTextColor(BLACK);
        roundTextView.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        roundTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        roundScore = new TextView(this);
        roundScore.setText(String.valueOf(0));
        roundScore.setTextSize(24);
        roundScore.setTextColor(BLACK);
        roundScore.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        roundScore.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        // Add TextViews to the score pair layout
        scorePairLayout.addView(terz1);
        scorePairLayout.addView(we);
        scorePairLayout.addView(you);
        scorePairLayout.addView(terz2);
        scorePairLayout.addView(roundTextView);
        scorePairLayout.addView(roundScore);

        // Add the score pair layout to the main scoresDisplayContainer
        scoresDisplayContainer.addView(scorePairLayout);
    }


    public void setRoundScore(String result) {
        final char firstChar = result.charAt(0);
        final EditText editText = new EditText(MainActivity.this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT); // Allow numbers and text
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // Center alignment

        // Create the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(editText);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String enteredText = editText.getText().toString().trim();
            if (!enteredText.isEmpty()) {
                quansh.setText("քուանշ");
                quansh.setBackgroundResource(R.drawable.border);
                roundNumber.setEnabled(true);
                scoreSet = true;
                said = false;
                quansh.setEnabled(said);

                try {
                    // Evaluate the entered expression manually
                    int enteredNumber = evaluateExpression(enteredText);

                    roundScore.setText(String.valueOf(enteredNumber)); // Update roundScore with entered number

                    int z = roundTerz1 + roundTerz2;
                    boolean kp = false;

                    if (enteredNumber == 162){
                        enteredNumber = 250;
                        kp = true;
                    } else {
                        kp = false;
                    }

                    if (firstChar == '+') {
                        if (enteredNumber + (roundTerz1 * 10) < roundNumberInt * 10) {
                            if (quanshed) {
                                totalTeam2 += (2 * roundNumberInt) + 16 + z;
                            } else if (sharped) {
                                totalTeam2 += (4 * roundNumberInt) + 16 + z;
                            } else {
                                totalTeam2 += 16 + roundNumberInt + z;
                            }
                        } else {
                            int roundedNumber = roundUp(enteredNumber);
                            if (quanshed && !sharped) {
                                totalTeam1 += (2 * roundNumberInt) + 16 + z;
                            } else if (quanshed && sharped) {
                                totalTeam1 += (4 * roundNumberInt) + 16 + z;
                            } else if (kp) {
                                totalTeam1 += 25 + roundTerz1 + roundNumberInt;
                            } else {
                                totalTeam1 += roundedNumber + roundNumberInt + roundTerz1;
                                totalTeam2 += roundTerz2 + (16 - roundedNumber);
                            }
                        }
                    } else {
                        if (enteredNumber + (roundTerz2 * 10) < roundNumberInt * 10) {
                            if (quanshed && !sharped) {
                                totalTeam1 += (2 * roundNumberInt) + 16 + z;
                            } else if (quanshed && sharped) {
                                totalTeam1 += (4 * roundNumberInt) + 16 + z;
                            } else {
                                totalTeam1 += 16 + roundNumberInt + z;
                            }
                        } else {
                            int roundedNumber = roundUp(enteredNumber);
                            if (quanshed && !sharped) {
                                totalTeam2 += (2 * roundNumberInt) + 16 + z;
                            } else if (quanshed && sharped) {
                                totalTeam2 += (4 * roundNumberInt) + 16 + z;
                            } else if (kp) {
                                totalTeam2 += 25 + roundTerz2 + roundNumberInt;
                            } else {
                                totalTeam2 += roundedNumber + roundNumberInt + roundTerz2;
                                totalTeam1 += roundTerz1 + (16 - roundedNumber);
                            }
                        }
                    }

                    you.setText(String.valueOf(totalTeam2));
                    we.setText(String.valueOf(totalTeam1));

                    quanshed = false;
                    sharped = false;

                    if (totalTeam1 >= 301) {
                        gameOver(1);
                    }
                    if (totalTeam2 >= 301) {
                        gameOver(2);
                    }

                    // Disable roundScore and EditText after processing
                    roundScore.setEnabled(false);
                    roundScore.setClickable(false);
                    roundScore.setFocusable(false);
                    editText.setEnabled(false);
                    textView4.setEnabled(false);
                    textView1.setEnabled(false);

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Please enter a valid number or expression", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }


    private int roundUp(double number) {
        double numberAfter = number / 10.0 - 0.6;
        return (int) Math.ceil(numberAfter);
    }

    private void gameOver(int team) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Team " + team + " wins!");
        builder.setMessage("Start over?");
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();

        // Customize TextView if needed
        TextView textView = dialog.findViewById(android.R.id.message);
        if (textView != null) {
            textView.setTextSize(18); // Example of customizing text size
        }
    }

    private void setQuansh(TextView quansh) {
        String roundNumberText = roundTextView.getText().toString();
        if (!quanshed) {
            quanshed = true;
            roundTextView.setText(roundNumberText + " ք");
            quansh.setBackgroundResource(R.drawable.button_bggrey);
            quansh.setText("սրել");
        } else {
            sharped = true;
            quanshed = false;
            if (roundNumberText.endsWith("ք")) {
                roundNumberText = roundNumberText.substring(0, roundNumberText.length() - 1) + "✓";
            } else {
                roundNumberText += "✓";
            }
            roundTextView.setText(roundNumberText);
            quansh.setBackgroundResource(R.drawable.button_bgyellow);
            quansh.setText("✓");
        }
    }

    private int evaluateExpression(String expression) {
        // Trim any whitespace from the expression
        expression = expression.replaceAll("\\s+", "");

        Stack<Integer> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();

        int currentNumber = 0;
        boolean buildingNumber = false;

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            if (Character.isDigit(currentChar)) {
                currentNumber = currentNumber * 10 + (currentChar - '0');
                buildingNumber = true;
            } else {
                if (buildingNumber) {
                    numbers.push(currentNumber);
                    currentNumber = 0;
                    buildingNumber = false;
                }

                if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                    while (!operations.isEmpty() && precedence(operations.peek()) >= precedence(currentChar)) {
                        numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
                    }
                    operations.push(currentChar);
                }
            }
        }

        if (buildingNumber) {
            numbers.push(currentNumber);
        }

        while (!operations.isEmpty()) {
            numbers.push(applyOperation(operations.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private int precedence(char operation) {
        if (operation == '+' || operation == '-') {
            return 1;
        } else if (operation == '*' || operation == '/') {
            return 2;
        }
        return -1;
    }

    private int applyOperation(char operation, int b, int a) {
        switch (operation) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
        }
        return 0;
    }
}