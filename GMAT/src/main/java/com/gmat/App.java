package com.gmat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.gmat.exceptions.FileFormatException;
import com.gmat.model.QuizSet;

/**
 * 
 * @author anish
 *
 */
public class App {

	private static int[] arrayOfLevel;
	private static int[] arrayOfTags;
	private static StringBuffer logString = new StringBuffer("");
	private static StringBuffer outputString = new StringBuffer("");

	/**
	 * Find the minimum number of quiz set which satisfies all the criteria
	 * 
	 * @param quizSet
	 * @return String
	 */
	public static String validQuiz(QuizSet quizSet) {
		int totalNumberOfQuestions = quizSet.getNoOfQuestions();
		int[] arrayOfLevel = quizSet.getArrayOfLevels();
		int[] arrayOfTags = quizSet.getArrayOfTags();

		int minLevel = findMin(arrayOfLevel);
		int minTag = findMin(arrayOfTags);
		int result = Math.min((totalNumberOfQuestions / 10), Math.min((minLevel / 2), minTag));
		return "Maximum number of Quiz set possible is " + result;
	}

	/**
	 * Finds the minimum value in the array
	 * 
	 * @param arrayOfLevel2
	 * @return int value
	 */
	private static int findMin(int[] arrayOfLevel) {

		int temp = Integer.MAX_VALUE;
		int size = arrayOfLevel.length;
		for (int i = 0; i < size; i++) {
			if (temp > arrayOfLevel[i])
				temp = arrayOfLevel[i];
		}
		return temp;
	}

	/**
	 * Reads the file present in the Specific folder Reads inly the file with the
	 * .txt extension Reads each line which should have a pattern like
	 * Q[1-100000]|LEVELS|TAGS Maximum number of Question allowed 100000 If a
	 * question having an Id more than 100000, eg: Q123456|HARD|Tag1 It will throw
	 * exception If level is invalid then it throws {@link FileFormatException} If
	 * tag is invalid then it throws {@link FileFormatException}
	 * 
	 * @param fileItem
	 * @return
	 * @throws FileFormatException
	 * @throws IOException
	 */
	private static QuizSet readFile(File fileItem) throws FileFormatException, IOException {
		BufferedReader br;
		int totalNumberOfQuestions = -1;
		Set<String> questionSet = new HashSet<String>();
		arrayOfLevel = new int[3];
		arrayOfTags = new int[6];
		if (fileItem.isFile() && fileItem.getName().endsWith(".txt")) {

			logString.append("Reading the file " + fileItem.getName() + System.getProperty("line.separator"));
			br = new BufferedReader(new FileReader(fileItem.getAbsoluteFile()));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					if(line.trim().isEmpty())
						continue;
					String inputString[] = line.split("\\|");
					if(inputString.length<3)
						throw new FileFormatException("Illegal fromat");
					
					String tags = inputString[2];
					String levels = inputString[1];
					if (Pattern.matches("^[Q|q]([1-9][0-9]{0,4}|100000)$", inputString[0])
							&& questionSet.add(inputString[0])) {
						readQuestionLevel(levels);
						readQuestionTags(tags);
					} else if (!Pattern.matches("^[Q|q]([1-9][0-9]{0,4}|100000)$", inputString[0])) {
						throw new FileFormatException("Maximum no of questions allowed in each file is 100000"
								+ System.getProperty("line.separator") + "Found : " + inputString[0]);

					}
				}
			} catch (FileFormatException e) {
				throw new FileFormatException(
						e.getMessage() + System.getProperty("line.separator") + "Occured at " + line);
			} finally {
				br.close();
			}
			totalNumberOfQuestions = questionSet.size();
			QuizSet quizSet = new QuizSet();
			quizSet.setArrayOfLevels(arrayOfLevel);
			quizSet.setArrayOfTags(arrayOfTags);
			quizSet.setNoOfQuestions(totalNumberOfQuestions);
			return quizSet;
		}
		return null;
	}

	/**
	 * read the question tags and do indexing in the arrayOfTags throws
	 * {@link FileFormatException}
	 * 
	 * @param tags
	 * @throws FileFormatException
	 */
	private static void readQuestionTags(String tags) throws FileFormatException {

		switch (tags) {
		case "Tag1":
			arrayOfTags[0]++;
			break;
		case "Tag2":
			arrayOfTags[1]++;
			break;
		case "Tag3":
			arrayOfTags[2]++;
			break;
		case "Tag4":
			arrayOfTags[3]++;
			break;
		case "Tag5":
			arrayOfTags[4]++;
			break;
		case "Tag6":
			arrayOfTags[5]++;
			break;
		default:
			throw new FileFormatException(tags + "  is invalid tag" + System.getProperty("line.separator")
					+ "Expected: Tag1/Tag2/Tag3/Tag4/Tag5/Tag6");
		}

	}

	/**
	 * read the question levels and do indexing in the arrayOfLevel throws
	 * {@link FileFormatException}
	 * 
	 * @param level
	 * @throws FileFormatException
	 */
	private static void readQuestionLevel(String levels) throws FileFormatException {

		switch (levels) {
		case "HARD":
			arrayOfLevel[0]++;
			break;
		case "MEDIUM":
			arrayOfLevel[1]++;
			break;
		case "EASY":
			arrayOfLevel[2]++;
			break;
		default:
			throw new FileFormatException(levels + "  is invalid level" + System.getProperty("line.separator")
					+ "Expected: HARD/EASY/MEDIUM");
		}
	}

	/**
	 * generates the output file
	 * 
	 * @param filePath
	 * @param fileName
	 */
	private static void generateFile(String filePath, String fileName) {
		FileWriter outPutFile = null;
		try {
			outPutFile = new FileWriter(filePath + File.separator + fileName);
			if (fileName.equals("output.txt"))
				outPutFile.write(outputString.toString());
			else
				outPutFile.write(logString.toString());
		} catch (IOException e) {
			System.out.println("File Generation failed");
			System.out.println(e.getMessage());
		} finally {
			try {
				outPutFile.close();
			} catch (IOException e) {
				System.out.println("File Cannot be closed");
				System.out.println(e.getMessage());
			}
		}

	}

	/**
	 * Main method Program execution starts from here
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String property = null;
		if(args.length==0) {
			System.out.println("Yes Zero");
			property = System.getProperty("user.dir");
		}
		else
			property = args[0];
		
		File f = new File(property);
		File inputFile = new File(f.getAbsolutePath() + File.separator + "input");
		System.out.println(inputFile.getAbsolutePath());
		File fileList[] = inputFile.listFiles();
		int flag = 0;
		for (File fileItem : fileList) {
			try {
				QuizSet quizSetRead = readFile(fileItem);
				String validQuiz = validQuiz(quizSetRead);
				outputString.append(
						validQuiz + " from the file " + fileItem.getName() + System.getProperty("line.separator"));
			} catch (NullPointerException e) {
				logString.append(e.getMessage() + System.getProperty("line.separator"));
				logString.append("Reading failed for the file: " + fileItem + System.getProperty("line.separator")
						+ "Only .txt file will be accepted" + System.getProperty("line.separator"));
			} catch (FileFormatException e) {
				logString.append(e.getMessage() + System.getProperty("line.separator"));
				logString.append("Reading failed for the file: " + fileItem + System.getProperty("line.separator"));
				flag++;
			} catch (IOException e) {
				logString.append(e.getMessage() + System.getProperty("line.separator"));
				logString.append("Reading failed for the file: " + fileItem + System.getProperty("line.separator"));
				flag++;
			} catch (Exception e) {
				logString.append(e.getMessage() + System.getProperty("line.separator"));
				logString.append("Reading failed for the file: " + fileItem + System.getProperty("line.separator"));
				flag++;
			} finally {
				logString.append("*************************Process finished for the file " + fileItem
						+ "**********************" + System.getProperty("line.separator"));
			}
		}

		if (!outputString.toString().equals(""))
			generateFile(f.getAbsolutePath(), "output.txt");
		generateFile(f.getAbsolutePath(), "logFile.txt");
		if (flag == 0)
			System.out.println("Process finished successfully without any errors, Please check the ouput.txt file");
		else if (flag > 0 && flag < fileList.length)
			System.out.println(
					"Process finished successfully with some errors, Please check the log.txt and ouput.txt file");
		else
			System.out.println("Process finished successfully without errors, Please check the log.txt file");
	}
}
