package myextractor.handlers;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import astUtil.Visitor;
import dataUtil.DBManipulation;
import extract.ClassInfoExtractor;
import extract.CommentInfoExtractor;
import extract.ExecuteExtractor;
import extract.MethodInvocationInfoExtractor;
import extract.ProjectName;
import file.Content;

import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
		
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        // Get all projects in the workspace
        IProject[] projects = root.getProjects();
        // Loop over all projects
        int num = projects.length;
        String projectNames = "";
        for (IProject project : projects) {
        	/*if (project.isOpen()) {
        		try {
            		if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
            			projectNames += project.getName() + " ";
                        ProjectName.setProjectName(project.getName());
                        
                        
                        IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();
                        // parse(JavaCore.create(project));
                        
                        for (IPackageFragment mypackage : packages) {
                        	if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
                        		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
                        			// Now create the AST for the ICompilationUnits
                                    CompilationUnit cu = parse(unit, project);
                                    Visitor visitor = new Visitor();
                                    cu.accept(visitor);
                                    }
                        		}
                        	}
                        }
            		} catch (CoreException e) {
                            e.printStackTrace();
                    }
        	}*/
        	try {
        		if (project.isOpen() && project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
        			projectNames += project.getName() + " ";
                    ProjectName.setProjectName(project.getName());
                    
                    IJavaProject javaProject = JavaCore.create(project);
                    IPackageFragment[] packages = javaProject.getPackageFragments();
                    // parse(JavaCore.create(project));
                    
                    for (IPackageFragment mypackage : packages) {
                    	if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
                    		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
                    			// Now create the AST for the ICompilationUnits
                                CompilationUnit cu = parse(unit, javaProject);
                                Visitor visitor = new Visitor();
                                cu.accept(visitor);
                                }
                    		}
                    	}
                    }
        		} catch (CoreException e) {
                        e.printStackTrace();
                }
        }
       /* String data = CommentInfoExtractor.getData();
		final String sqlStatement = "insert into commentinfo values" + data.substring(0, data.length() - 1);
		DBManipulation.sqlExecute(sqlStatement);*/
		//ExecuteExtractor.extracts("D:\\eclipse\\workspace\\MyExtractor");
		//System.out.println("hello plug in.");
        
        long start = System.currentTimeMillis();
		
		ArrayList<ArrayList<String>> data /*= CommentInfoExtractor.getData()*/;
		long t = System.currentTimeMillis();
		/*if (!data.isEmpty()) {
			DBManipulation.DoStore("commentinfo", data);
		}
		else {
			System.out.println("failed !");
		}
		CommentInfoExtractor.resetData();*/
		
		/*data = MethodInfoExtractor.getData();
		
		
		if (!data.isEmpty()) {
			DBManipulation.DoStore("methodinfo", data);
		}
		else {
			System.out.println("failed !");
		}
		MethodInfoExtractor.resetData();*/
		
		data = ClassInfoExtractor.getData();
		ClassInfoExtractor.resetData();
		if (!data.isEmpty()) {
			DBManipulation.DoStore("classinfo", data);
		}
		else {
			System.out.println("failed !");
		}
		
		data = MethodInvocationInfoExtractor.getData();
		MethodInvocationInfoExtractor.resetData();
		if (!data.isEmpty()) {
			DBManipulation.DoStore("methodinvocationinfo", data);
		}
		else {
			System.out.println("failed !");
		}
		
		long end = System.currentTimeMillis();
		System.out.println("getData用时：" + (t - start));
		System.out.println("插入数据库用时：" + (end - t));
		System.out.println(ProjectName.getProjectName());
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"MyExtractor",
				"successful!\nthere are " + num + " projects: \n" + projectNames);
		
		return null;
	}
	
    /**
     * Reads a ICompilationUnit and creates the AST DOM for manipulating the
     * Java source file
     *
     * @param unit
     * @return
     */

    private static CompilationUnit parse(ICompilationUnit unit, IJavaProject javaProject) {
            ASTParser parser = ASTParser.newParser(AST.JLS8);
            parser.setKind(ASTParser.K_COMPILATION_UNIT);
            parser.setSource(unit);
            //Content.setFileName(unit.getPath().toFile().getAbsolutePath());
            Content.setFileName(unit.getPath().toOSString());
            parser.setProject(javaProject);
            parser.setUnitName(unit.getElementName());
            parser.setResolveBindings(true);
            return (CompilationUnit) parser.createAST(null); // parse
    }
}
